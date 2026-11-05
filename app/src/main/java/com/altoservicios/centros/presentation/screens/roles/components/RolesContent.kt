package com.altoservicios.centros.presentation.screens.roles.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel
import com.altoservicios.centros.presentation.screens.roles.RolesViewModel

@Composable
fun RolesContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    vm: RolesViewModel = hiltViewModel(),
    onLogout: () -> Unit = {}
) {
    val roles = vm.authResponse.user?.roles.orEmpty()
    val profileViewModel: ProfileViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.colorBgSecondary))
            .padding(paddingValues)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(text = "Selecciona un rol", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when {
                vm.isLoading -> CircularProgressIndicator()
                roles.isEmpty() -> Text(text = "No hay roles disponibles para esta cuenta.")
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = roles) { rol ->
                        RolesItem(rol = rol, navController = navController)
                    }
                }
            }
        }

        DefaultButton(
            modifier = Modifier,
            text = "Salir",
            onClick = { profileViewModel.logout(onLogout) }
        )
    }
}