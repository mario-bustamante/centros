package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.PhotoDeleteResponse
import com.altoservicios.centros.domain.model.PhotoResponse
import com.altoservicios.centros.domain.model.PhotoUpdateResponse
import com.altoservicios.centros.domain.model.PhotoUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {

    @GET("user/residence/images/{idResidence}")
    suspend fun getImages(
        @Path("idResidence") idResidence: String,
    ): Response<PhotoResponse>

    @PUT("user/residence/image")
    suspend fun updateImage(
        @Query("residence_id") residenceId: String,
        @Query("id") id: Int?,
        @Query("title") title: String?,
        @Query("description") description: String?,
        @Query("principal") principal: Int?
    ): Response<PhotoUpdateResponse>

    @DELETE("user/residence/image/delete")
    suspend fun deleteImage(
        @Query("residence_id") residenceId: String,
        @Query("id") id: Int?
    ): Response<PhotoDeleteResponse>

    @Multipart
    @POST("user/residence/image/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("residence_id") residenceId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("principal") principal: RequestBody
    ): Response<PhotoUploadResponse>
}
