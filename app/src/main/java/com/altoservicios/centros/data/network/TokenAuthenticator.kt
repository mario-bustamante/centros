package com.altoservicios.centros.data.network

import com.altoservicios.centros.data.dataSource.remote.service.AuthService
import com.altoservicios.centros.data.datastore.AuthDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Ante un 401 renueva el token con auth/refresh y reintenta la peticion una vez.
 * [refreshService] debe usar un cliente sin este authenticator para evitar recursion.
 */
class TokenAuthenticator(
    private val authDataStore: AuthDataStore,
    private val refreshService: AuthService
) : Authenticator {

    private val lock = Any()

    override fun authenticate(route: Route?, response: Response): Request? {
        // login/register/refresh devuelven 401 por credenciales o token invalidos, no se reintentan
        if (response.request.url.encodedPath.contains("/auth/")) return null
        if (response.request.header("Authorization") == null) return null
        if (responseCount(response) > 1) return null

        synchronized(lock) {
            val session = runBlocking { authDataStore.getData().first() }
            val current = session.token
            if (current.isNullOrBlank()) return null

            // Otra peticion concurrente ya renovo el token
            if (response.request.header("Authorization") != "Bearer $current") {
                return response.request.withToken(current)
            }

            val refreshed = try {
                runBlocking { refreshService.refresh() }
            } catch (e: Exception) {
                return null
            }

            val newToken = refreshed.body()?.token
            if (refreshed.isSuccessful && !newToken.isNullOrBlank()) {
                runBlocking { authDataStore.saveUser(session.withRefreshedToken(refreshed.body()!!)) }
                return response.request.withToken(newToken)
            }

            // Token expirado o invalido: la sesion ya no sirve
            if (refreshed.code() == 401) runBlocking { authDataStore.delete() }
            return null
        }
    }

    private fun Request.withToken(token: String): Request =
        newBuilder().header("Authorization", "Bearer $token").build()

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
