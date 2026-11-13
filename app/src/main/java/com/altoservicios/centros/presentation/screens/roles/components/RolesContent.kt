package com.altoservicios.centros.presentation.screens.roles.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel
import com.altoservicios.centros.presentation.screens.roles.RolesViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun RolesContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    vm: RolesViewModel = hiltViewModel(),
    onLogout: () -> Unit = {}
) {
    val roles = vm.authResponse.user?.roles.orEmpty()
    val profileViewModel: ProfileViewModel = hiltViewModel()

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val logoResource = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        R.drawable.ic_logo_h
    } else {
        R.drawable.ic_logo_v
    }
    val logoIsVertical = logoResource == R.drawable.ic_logo_h

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.colorBgSecondary))
            .padding(paddingValues)
            .padding(top = 40.dp, start = 10.dp, end = 10.dp, bottom = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (!logoIsVertical) {
            Image(
                modifier = Modifier
                    .height((configuration.screenHeightDp * 0.30f).dp)
                    .padding(all = 10.dp),
                painter = painterResource(id = logoResource),
                contentDescription = "Image",
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(
                topEnd = 40.dp,
                topStart = 40.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.7f)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(text = "Selecciona un rol", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

                Spacer(Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when {
                        vm.isLoading -> CircularProgressIndicator()
                        roles.isEmpty() -> Text(text = "No hay roles disponibles para esta cuenta.")
                        else -> LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(items = roles) { rol ->
                                RolesItem(rol = rol, navController = navController)
                            }
                        }
                    }
                }

                /*
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DefaultButton(
                        modifier = Modifier,
                        text = "Salir",
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        onClick = { profileViewModel.logout(onLogout) }
                    )
                }
                */
            }
        }
    }
}