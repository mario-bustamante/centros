package com.altoservicios.centros.presentation.screens.auth.login.components

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.presentation.components.ProgressBar
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.AuthScreen
import com.altoservicios.centros.presentation.navigation.screen.RolesScreen
import com.altoservicios.centros.presentation.screens.auth.login.LoginViewModel
import androidx.compose.ui.res.stringResource


@Composable
fun Login(navController: NavHostController, vm: LoginViewModel = hiltViewModel()) {
    when(val response = vm.loginResponse) {
        Resource.Loading -> {
            ProgressBar()
        }

        is Resource.Success -> {
            LaunchedEffect(Unit) {
                vm.saveSession(response.data)
                Log.d("LoginUsuario", "LoginUsuario: ${response.data}")
                val roles = response.data.user?.roles.orEmpty()
                if (roles.size > 1) {
                    navController.navigate(route = Graph.ROLES) {
                        popUpTo(Graph.AUTH) { inclusive = true }
                    }
                } else {
                    navController.navigate(route = Graph.CLIENT) {
                        popUpTo(Graph.AUTH) { inclusive = true }
                    }
                }
            }
        }

        is Resource.Failure -> {
            Toast.makeText(LocalContext.current, response.message, Toast.LENGTH_SHORT).show()
        }

        else -> {
            if(response != null) {
                Toast.makeText(LocalContext.current,  stringResource(R.string.msg_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}