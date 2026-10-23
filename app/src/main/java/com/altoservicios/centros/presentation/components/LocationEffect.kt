package com.altoservicios.centros.presentation.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

data class UserLocation(val location: Location?, val hasPermission: Boolean)

/** Solicita el permiso de ubicacion y devuelve la ultima ubicacion conocida del usuario. */
@SuppressLint("MissingPermission")
@Composable
fun rememberUserLocation(): UserLocation {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var location by remember { mutableStateOf<Location?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        hasPermission = it
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            LocationServices.getFusedLocationProviderClient(context).lastLocation
                .addOnSuccessListener { location = it }
        }
    }

    return UserLocation(location, hasPermission)
}

fun distanceLabel(from: Location, latitude: Double, longitude: Double): String {
    val results = FloatArray(1)
    Location.distanceBetween(from.latitude, from.longitude, latitude, longitude, results)
    val meters = results[0]
    return if (meters > 1000f) "%.1f KM".format(meters / 1000f) else "%.0f M".format(meters)
}
