package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.altoservicios.centros.domain.model.Photo

object PhotosScreen : MenuScreen {
    override val route = "client/photos"
    override val title = "Fotografías"
    override val icon: ImageVector = Icons.Default.Star

    const val PHOTO = "client/photo?photo={photo}"

    fun photo(photo: Photo?) =
        if (photo == null) "client/photo" else "client/photo?photo=${Uri.encode(photo.toJson())}"
}