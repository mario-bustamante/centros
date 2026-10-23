package com.altoservicios.centros.presentation.screens.client.residences.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.distanceLabel
import com.altoservicios.centros.presentation.components.rememberUserLocation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ClientResidenceMapScreen(navController: NavHostController, residence: Residence) {
    val user = rememberUserLocation()
    val cameraState = rememberCameraPositionState()

    val lat = residence.latitude?.toDoubleOrNull()
    val lng = residence.longitude?.toDoubleOrNull()
    val position = if (lat != null && lng != null && lat > 0) LatLng(lat, lng) else null

    LaunchedEffect(position) {
        position?.let { cameraState.animate(CameraUpdateFactory.newLatLngZoom(it, 12f)) }
    }

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = residence.name.orEmpty(),
                upAvailable = true,
                navController = navController
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraState,
                properties = MapProperties(
                    mapType = MapType.TERRAIN,
                    isMyLocationEnabled = user.hasPermission
                ),
                uiSettings = MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = user.hasPermission)
            ) {
                position?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = residence.name,
                        snippet = listOfNotNull(residence.address, residence.province).joinToString(", "),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
                    user.location?.let { location ->
                        Polyline(points = listOf(LatLng(location.latitude, location.longitude), it), width = 5f)
                    }
                }
            }

            if (user.location != null && position != null) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = residence.name.orEmpty(), fontWeight = FontWeight.Bold)
                        Text(
                            text = stringResource(R.string.label_distance) + " " +
                                distanceLabel(user.location, position.latitude, position.longitude)
                        )
                    }
                }
            }
        }
    }
}
