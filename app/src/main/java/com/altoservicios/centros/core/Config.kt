package com.altoservicios.centros.core

import com.altoservicios.centros.BuildConfig

object Config {
    const val BASE_URL = "http://10.0.2.2:8000/api/"

    // En desarrollo (emulador) las imagenes salen del servidor local, que solo tiene la variante 125x125
    val IMAGE_URL = if (BuildConfig.DEBUG) {
        "http://10.0.2.2:8000/img/residencia"
    } else {
        "https://residenciasysalud.es/img/residencia"
    }
    val IMAGE_SIZE = if (BuildConfig.DEBUG) "125x125" else "240x160"
    const val AUTH_PREFERENCE = "AUTH_PREF"
    const val AUTH_KEY = "AUTH_KEY"
}