package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.ContactValuesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ContactService {

    @GET("contact/values")
    suspend fun getValues(): Response<ContactValuesResponse>

    // TODO: POST contact/email (ContactController@enviarMensaje). En Laravel el cuerpo del metodo esta comentado.
    //  Cuando exista, enviar los campos del formulario (formulario, nombre, email, telefono, observaciones,
    //  id_residencia, fecha, etc.) y el token de reCAPTCHA. Luego conectar repositorio, caso de uso y
    //  HomeContactViewModel.send().
    //@FormUrlEncoded
    //@POST("contact/email")
    //suspend fun sendMessage(@FieldMap fields: Map<String, String>): Response<Unit>

}
