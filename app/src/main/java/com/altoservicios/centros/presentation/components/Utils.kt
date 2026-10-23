package com.altoservicios.centros.presentation.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.altoservicios.centros.core.Config

fun imageUrl(path: String?): String? =
    path?.takeIf { it.isNotBlank() }?.let { "${Config.IMAGE_URL}/$it-240x160.webp" }

fun Context.dial(phone: String?) {
    if (phone.isNullOrBlank()) return
    startActivitySafely(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
}

fun Context.openWhatsApp(phone: String?) {
    val digits = phone?.filter { it.isDigit() }
    if (digits.isNullOrBlank()) return
    startActivitySafely(Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$digits")))
}

private fun Context.startActivitySafely(intent: Intent) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}
