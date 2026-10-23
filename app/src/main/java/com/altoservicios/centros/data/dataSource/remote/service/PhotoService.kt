package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.model.PhotoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface PhotoService {

    @GET("images")
    suspend fun getImages(): Response<PhotoResponse>

    @PUT("image")
    suspend fun updateImage(
        @Query("id") id: Int?,
        @Query("title") title: String?,
        @Query("description") description: String?,
        @Query("principal") principal: Int?
    ): Response<Photo>

    @DELETE("image/delete")
    suspend fun deleteImage(@Query("id") id: Int?): Response<Photo>

    @Multipart
    @POST("image/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("principal") principal: RequestBody
    ): Response<Unit>
}
