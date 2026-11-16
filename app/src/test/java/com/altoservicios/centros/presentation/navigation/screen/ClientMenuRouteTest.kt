package com.altoservicios.centros.presentation.navigation.screen

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ClientMenuRouteTest {
    @Test
    fun usesSelectedResidenceForEachResidenceScreen() {
        assertEquals("client/residence/42", clientMenuRoute(ClientScreen.Residence, "42"))
        assertEquals("client/rooms/42", clientMenuRoute(ClientScreen.Rooms, "42"))
        assertEquals("client/photos/42", clientMenuRoute(PhotosScreen, "42"))
        assertEquals("client/rooms/73", clientMenuRoute(ClientScreen.Rooms, "73"))
    }

    @Test
    fun doesNotNavigateToResidenceScreensWithoutAnId() {
        assertNull(clientMenuRoute(ClientScreen.Residence, null))
        assertNull(clientMenuRoute(ClientScreen.Rooms, ""))
        assertNull(clientMenuRoute(PhotosScreen, "{residenceId}"))
        assertEquals(ClientScreen.Profile.route, clientMenuRoute(ClientScreen.Profile, null))
    }
}
