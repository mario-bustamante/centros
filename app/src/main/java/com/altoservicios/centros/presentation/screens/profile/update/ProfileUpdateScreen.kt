package com.altoservicios.centros.presentation.screens.profile.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.FormTextField
import com.altoservicios.centros.presentation.components.MessageEffect

@Composable
fun ProfileUpdateScreen(navController: NavHostController, vm: ProfileUpdateViewModel = hiltViewModel()) {
    val successMessage = stringResource(R.string.toast_update_profile)

    MessageEffect(message = vm.message, onConsumed = { vm.message = "" })

    LaunchedEffect(vm.finished) {
        if (vm.finished) navController.popBackStack()
    }

    Scaffold(
        topBar = { DefaultTopBar(title = "Actualizar Información", upAvailable = true, navController = navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
        ) {
            vm.user?.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    FormTextField(
                        label = stringResource(R.string.label_username),
                        value = user.name,
                        onValueChange = { vm.onUserChange(user.copy(name = it)) }
                    )
                    FormTextField(
                        label = stringResource(R.string.label_name),
                        value = user.fullName.orEmpty(),
                        onValueChange = { vm.onUserChange(user.copy(fullName = it)) }
                    )
                    FormTextField(
                        label = stringResource(R.string.lbl_email),
                        value = user.email.orEmpty(),
                        onValueChange = {},
                        keyboardType = KeyboardType.Email,
                        enabled = false
                    )
                    FormTextField(
                        label = stringResource(R.string.label_phone),
                        value = user.phone.orEmpty(),
                        onValueChange = { vm.onUserChange(user.copy(phone = it)) },
                        keyboardType = KeyboardType.Phone
                    )
                    FormTextField(
                        label = stringResource(R.string.label_address),
                        value = user.address.orEmpty(),
                        onValueChange = { vm.onUserChange(user.copy(address = it)) }
                    )

                    DefaultButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.btn_save),
                        onClick = { vm.save(successMessage) }
                    )
                }
            }

            if (vm.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
