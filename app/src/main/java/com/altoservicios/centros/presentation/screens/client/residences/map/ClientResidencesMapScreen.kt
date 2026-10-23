package com.altoservicios.centros.presentation.screens.client.residences.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.dial
import com.altoservicios.centros.presentation.components.distanceLabel
import com.altoservicios.centros.presentation.components.openWhatsApp
import com.altoservicios.centros.presentation.components.rememberUserLocation
import com.altoservicios.centros.presentation.navigation.screen.ClientRoute
import com.altoservicios.centros.presentation.screens.client.residences.ClientResidencesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

private val DEFAULT_CENTER = LatLng(40.3, -3.74922)
private const val DEFAULT_ZOOM = 7.5f

private fun Residence.position(): LatLng? {
    val lat = latitude?.toDoubleOrNull() ?: return null
    val lng = longitude?.toDoubleOrNull() ?: return null
    return if (lat > 0) LatLng(lat, lng) else null
}

@Composable
fun ClientResidencesMapScreen(navController: NavHostController, vm: ClientResidencesViewModel) {
    val context = LocalContext.current
    val user = rememberUserLocation()
    val cameraState = rememberCameraPositionState()
    var selected by remember { mutableStateOf<Residence?>(null) }

    MessageEffect(message = vm.errorMessage, onConsumed = { vm.errorMessage = "" })

    // Se recarga al cambiar los filtros o al conocer la ubicacion del usuario
    LaunchedEffect(vm.search, user.location) {
        selected = null
        vm.loadMap(user.location?.latitude, user.location?.longitude)
    }

    // Centra el mapa en el promedio de las residencias, o en el usuario si no hay resultados
    LaunchedEffect(vm.mapResidences, user.location) {
        val positions = vm.mapResidences.mapNotNull { it.position() }
        val center = when {
            positions.isNotEmpty() -> LatLng(
                positions.sumOf { it.latitude } / positions.size,
                positions.sumOf { it.longitude } / positions.size
            )
            user.location != null -> LatLng(user.location.latitude, user.location.longitude)
            else -> DEFAULT_CENTER
        }
        cameraState.animate(CameraUpdateFactory.newLatLngZoom(center, DEFAULT_ZOOM))
    }

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Residencias en Mapa",
                actions = {
                    IconButton(onClick = { navController.navigate(ClientRoute.SEARCH) }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraState,
                properties = MapProperties(isMyLocationEnabled = user.hasPermission),
                uiSettings = MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = user.hasPermission),
                onMapClick = { selected = null }
            ) {
                vm.mapResidences.forEach { residence ->
                    residence.position()?.let { position ->
                        Marker(
                            state = MarkerState(position = position),
                            title = residence.name,
                            snippet = residence.address,
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
                            onClick = {
                                selected = residence
                                false
                            }
                        )
                    }
                }

                val selectedPosition = selected?.position()
                if (user.location != null && selectedPosition != null) {
                    Polyline(
                        points = listOf(LatLng(user.location.latitude, user.location.longitude), selectedPosition),
                        width = 10f
                    )
                }
            }

            if (vm.isLoadingMap) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.TopCenter).padding(16.dp))
            }

            selected?.let { residence ->
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = residence.name.orEmpty(), fontWeight = FontWeight.Bold)
                        Text(text = listOfNotNull(residence.address, residence.province).joinToString(", "))

                        val position = residence.position()
                        if (user.location != null && position != null) {
                            Text(
                                text = stringResource(R.string.label_distance) + " " +
                                    distanceLabel(user.location, position.latitude, position.longitude)
                            )
                        }

                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            DefaultButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 4.dp),
                                text = "Ficha",
                                onClick = { navController.navigate(ClientRoute.detail(residence)) }
                            )
                            DefaultButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp),
                                text = stringResource(R.string.btn_call),
                                onClick = { context.dial(residence.phone) }
                            )
                            DefaultButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp),
                                text = stringResource(R.string.btn_whatsapp),
                                onClick = { context.openWhatsApp(residence.phone) }
                            )
                        }
                    }
                }
            }
        }
    }
}
