package com.altoservicios.centros.presentation.screens.home.contact

import com.altoservicios.centros.domain.model.Contacto
import com.altoservicios.centros.domain.model.Persona
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.google.gson.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ContactSubmissionTest {
    @Test
    fun formatsAllEnteredFieldsForWhatsappAndJson() {
        val submission = ContactSubmission(
            contacto = Contacto(
                nombre = "Ana",
                email = "ana@example.test",
                codigo = "+34",
                telefono = "123456789",
                urgencia = 2,
                presupuesto = 1800,
                parentesco = 3,
                preferencia = "Cerca del centro",
                observaciones = "Contacto por la tarde"
            ),
            personas = listOf(
                Persona(trato = 1, nombre = "Juan", domicilio = "Calle 1", edad = "80", obs = "Primera"),
                Persona(trato = 2, nombre = "Eva", domicilio = "Calle 2", edad = "79", obs = "Segunda")
            )
        )
        val valores = ValoresFormulario(
            urgencia = listOf(2 to "Pronto"),
            parentesco = listOf(3 to "Familiar"),
            trato = listOf(1 to "Señor", 2 to "Señora")
        )

        val message = submission.toWhatsAppMessage(valores)
        listOf(
            "Ana", "ana@example.test", "+34", "123456789", "Pronto", "1800",
            "Familiar", "Cerca del centro", "Contacto por la tarde",
            "Señor", "Juan", "Calle 1", "80", "Primera",
            "Señora", "Eva", "Calle 2", "79", "Segunda"
        ).forEach { assertTrue("Falta $it", message.contains(it)) }

        val json = JsonParser.parseString(submission.toJson()).asJsonObject
        assertEquals(1800, json.getAsJsonObject("contacto").get("presupuesto").asInt)
        assertEquals(3, json.getAsJsonObject("contacto").get("parentesco").asInt)
        assertEquals("123456789", json.getAsJsonObject("contacto").get("telefono").asString)
        assertEquals(2, json.getAsJsonArray("personas").size())
        assertEquals("Segunda", json.getAsJsonArray("personas")[1].asJsonObject.get("obs").asString)
    }

    @Test
    fun representsMissingValuesAndUnrecognizedOptions() {
        val submission = ContactSubmission(
            Contacto(urgencia = 42, parentesco = null),
            listOf(Persona(trato = null))
        )

        val message = submission.toWhatsAppMessage(ValoresFormulario())
        assertTrue(message.contains("Urgencia: 42"))
        assertTrue(message.contains("Parentesco: No indicado"))
        val json = JsonParser.parseString(submission.toJson()).asJsonObject
        assertTrue(json.getAsJsonObject("contacto").get("nombre").isJsonNull)
        assertTrue(json.getAsJsonArray("personas")[0].asJsonObject.get("trato").isJsonNull)
    }
}
