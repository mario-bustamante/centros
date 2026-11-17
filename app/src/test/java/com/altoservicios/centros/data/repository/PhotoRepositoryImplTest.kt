package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.PhotoService
import com.altoservicios.centros.domain.model.PhotoDeleteResponse
import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.model.PhotoResponse
import com.altoservicios.centros.domain.model.PhotoUpdateResponse
import com.altoservicios.centros.domain.model.PhotoUploadResponse
import com.altoservicios.centros.domain.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import java.io.File

class PhotoRepositoryImplTest {
    @Test
    fun returnsImagesAndimageCountFromResponse() = runBlocking {
        val response = Gson().fromJson(
            """{"data":[{"id":1,"path":"3/foto"}],"meta":{"image_count":6}}""",
            PhotoResponse::class.java
        )

        val result = PhotoRepositoryImpl(FakePhotoService(response)).getImages("3")

        assertTrue(result is Resource.Success)
        val photoResponse = (result as Resource.Success).data
        assertEquals(6, photoResponse.meta?.imageCount)
        assertEquals("3/foto", photoResponse.data?.single()?.path)
    }

    @Test
    fun reportsMissingimageCount() = runBlocking {
        val response = Gson().fromJson("""{"data":[],"meta":{}}""", PhotoResponse::class.java)

        val result = PhotoRepositoryImpl(FakePhotoService(response)).getImages("3")

        assertTrue(result is Resource.Failure)
        assertEquals("La respuesta de imágenes no contiene image_count", (result as Resource.Failure).message)
    }

    @Test
    fun uploadRequiresImageObject() = runBlocking {
        val gson = Gson()
        val valid = gson.fromJson("""{"data":{"id":12,"principal":true}}""", PhotoUploadResponse::class.java)
        val service = FakePhotoService(PhotoResponse(), valid)
        val repository = PhotoRepositoryImpl(service)
        val image = File("unused-test-image.jpg")

        assertTrue(repository.storeImage("3", image, Photo()) is Resource.Success)

        for (json in listOf("""{"data":null}""", """{}""", """{"data":"invalid"}""", """{"data":[]}""")) {
            service.uploadResponse = gson.fromJson(json, PhotoUploadResponse::class.java)
            val result = repository.storeImage("3", image, Photo())
            assertTrue(result is Resource.Failure)
            assertEquals("La respuesta de carga no contiene la imagen", (result as Resource.Failure).message)
        }
    }

    private class FakePhotoService(
        private val response: PhotoResponse,
        var uploadResponse: PhotoUploadResponse? = null
    ) : PhotoService {
        override suspend fun getImages(idResidence: String): Response<PhotoResponse> =
            Response.success(response)

        override suspend fun storeImage(
            image: MultipartBody.Part,
            residenceId: RequestBody,
            title: RequestBody,
            description: RequestBody,
            principal: RequestBody
        ): Response<PhotoUploadResponse> = Response.success(requireNotNull(uploadResponse))

        override suspend fun updateImage(
            residenceId: String,
            id: Int?,
            title: String?,
            description: String?,
            principal: Int?
        ): Response<PhotoUpdateResponse> = error("Not used")

        override suspend fun deleteImage(
            residenceId: String,
            id: Int?
        ): Response<PhotoDeleteResponse> = error("Not used")
    }
}
