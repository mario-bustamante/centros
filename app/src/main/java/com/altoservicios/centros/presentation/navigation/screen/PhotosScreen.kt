package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.altoservicios.centros.domain.model.Photo

object PhotosScreen : MenuScreen {
    override val route = "client/photos/{residenceId}"
    override val title = "Fotografías"
    override val icon: ImageVector = Icons.Default.Star

    const val PHOTO = "client/photo?residenceId={residenceId}&photo={photo}"

    fun createRoute(residenceId: String) = "client/photos/$residenceId"

    fun photo(residenceId: String, photo: Photo?) =
        if (photo == null) {
            "client/photo?residenceId=$residenceId"
        } else {
            "client/photo?residenceId=$residenceId&photo=${Uri.encode(photo.toJson())}"
        }
}