package com.altoservicios.centros.presentation.screens.home.contact

import android.content.ActivityNotFoundException
import android.content.ContextWrapper
import android.content.Intent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.domain.repository.ContactRepository
import com.altoservicios.centros.domain.useCase.contact.ContactUseCase
import com.altoservicios.centros.domain.useCase.contact.GetContactValuesUseCase
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.presentation.screens.home.contact.components.HomeContactContent
import com.google.gson.JsonParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ContactFlowTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun navigatesFromContactThroughDataToEitherPersonStep() {
        lateinit var vm: HomeContactViewModel
        val openedIntents = mutableListOf<Intent>()
        var noHandler = false
        composeRule.runOnUiThread {
            val repository = object : ContactRepository {
                override suspend fun getValues(): Resource<ValoresFormulario> =
                    Resource.Success(ValoresFormulario())
            }
            vm = HomeContactViewModel(ContactUseCase(GetContactValuesUseCase(repository)))
            vm.phoneWhatsapp = ""
        }
        composeRule.setContent {
            val baseContext = LocalContext.current
            val context = remember(baseContext) {
                object : ContextWrapper(baseContext) {
                    override fun startActivity(intent: Intent) {
                        if (noHandler) throw ActivityNotFoundException()
                        openedIntents.add(Intent(intent))
                    }
                }
            }
            CompositionLocalProvider(LocalContext provides context) {
                HomeContactContent(vm)
            }
        }

        composeRule.runOnIdle { assertEquals(ContactStep.CONTACT, vm.step) }
        composeRule.onNodeWithText("Continuar").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals(ContactStep.DATA, vm.step)
            vm.contacto = vm.contacto.copy(parentesco = 1, nombre = "Ana")
        }
        composeRule.onNodeWithText("Continuar").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals(ContactStep.OWN_PERSON, vm.step)
            assertEquals("Ana", vm.personas[0].nombre)
            vm.personas[0] = vm.personas[0].copy(nombre = "Ana María")
        }
        composeRule.onNodeWithText("Enviar").performScrollTo().performClick()
        composeRule.runOnIdle {
            val payload = JsonParser.parseString(vm.jsonPayload).asJsonObject
            assertEquals(1, payload.getAsJsonArray("personas").size())
            assertEquals("Falta el número de WhatsApp", vm.errorMessage)
            assertTrue(openedIntents.isEmpty())
            vm.phoneWhatsapp = "+34 123-456-789"
            noHandler = true
        }
        composeRule.onNodeWithText("Enviar").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals("No se pudo abrir WhatsApp", vm.errorMessage)
            assertTrue(openedIntents.isEmpty())
            noHandler = false
        }
        composeRule.onNodeWithText("Enviar").performScrollTo().performClick()
        composeRule.runOnIdle {
            val intent = openedIntents.single()
            assertEquals(Intent.ACTION_VIEW, intent.action)
            assertEquals("https", intent.data?.scheme)
            assertEquals("wa.me", intent.data?.host)
            assertEquals("/34123456789", intent.data?.path)
            assertEquals(vm.whatsappMessage, intent.data?.getQueryParameter("text"))
            assertEquals("", vm.errorMessage)
        }
        composeRule.onNodeWithText("Volver").performScrollTo().performClick()
        composeRule.onNodeWithText("Continuar").performScrollTo().performClick()
        composeRule.runOnIdle { assertEquals("Ana María", vm.personas[0].nombre) }
        composeRule.onNodeWithText("Volver").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals(ContactStep.DATA, vm.step)
            vm.contacto = vm.contacto.copy(parentesco = 2)
        }
        composeRule.onNodeWithText("Continuar").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals(ContactStep.OTHER_PERSONS, vm.step)
            vm.personas[0] = vm.personas[0].copy(nombre = "Otra persona")
        }
        composeRule.onNodeWithText("Volver").performScrollTo().performClick()
        composeRule.onNodeWithText("Continuar").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals(ContactStep.OTHER_PERSONS, vm.step)
            assertEquals("Otra persona", vm.personas[0].nombre)
            vm.phoneWhatsapp = ""
        }
        composeRule.onNodeWithText("Enviar").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals("Falta el número de WhatsApp", vm.errorMessage)
            val payload = JsonParser.parseString(vm.jsonPayload).asJsonObject
            assertEquals(2, payload.getAsJsonArray("personas").size())
            assertEquals("Otra persona", payload.getAsJsonArray("personas")[0].asJsonObject.get("nombre").asString)
            assertEquals(1, openedIntents.size)
            vm.phoneWhatsapp = "+34 123-456-789"
        }
        composeRule.onNodeWithText("Enviar").performScrollTo().performClick()
        composeRule.runOnIdle {
            assertEquals(2, openedIntents.size)
            val uri = openedIntents.last().data
            assertEquals("/34123456789", uri?.path)
            assertEquals(vm.whatsappMessage, uri?.getQueryParameter("text"))
            assertTrue(uri?.getQueryParameter("text")?.contains("Otra persona") == true)
            assertEquals("", vm.errorMessage)
        }
        composeRule.onNodeWithText("Volver").performScrollTo().performClick()
        composeRule.onNodeWithText("Volver").performScrollTo().performClick()
        composeRule.runOnIdle { assertEquals(ContactStep.CONTACT, vm.step) }
    }
}
