package com.altoservicios.centros.domain.model

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PhotoUploadResponseTest {
    private val gson = Gson()

    @Test
    fun parsesUploadedImageObject() {
        val response = gson.fromJson(
            """{"data":{"id":12,"imagen_url":"3/foto","titulo":"Foto","principal":true}}""",
            PhotoUploadResponse::class.java
        )

        assertEquals(12, response.data?.get("id")?.asInt)
        assertEquals("3/foto", response.data?.get("imagen_url")?.asString)
        assertEquals(true, response.data?.get("principal")?.asBoolean)
    }

    @Test
    fun leavesMissingImageAsNull() {
        val response = gson.fromJson("""{"data":null}""", PhotoUploadResponse::class.java)

        assertNull(response.data)
    }
}
