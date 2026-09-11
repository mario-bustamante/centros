package com.altoservicios.centros.presentation.screens.profile.components

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.screens.MainActivity
import com.altoservicios.centros.presentation.screens.profile.ProfileViewModel

@Composable
fun ProfileContent(paddingValues: PaddingValues, vm: ProfileViewModel = hiltViewModel()) {

    val activity = LocalActivity.current

    Column(modifier = Modifier.padding(paddingValues)) {
        Text(text = "Perfil")
        DefaultButton(
            modifier = Modifier,
            text = "Cerrar sesion",
            onClick = {
                vm.logout {
                    activity?.let { currentActivity ->
                        currentActivity.finish()
                        currentActivity.startActivity(
                            Intent(currentActivity, MainActivity::class.java)
                        )
                    }
                }
            }
        )
    }
}