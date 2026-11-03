package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.ContactValuesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ContactService {

    @GET("contact/values")
    suspend fun getValues(): Response<ContactValuesResponse>
}
