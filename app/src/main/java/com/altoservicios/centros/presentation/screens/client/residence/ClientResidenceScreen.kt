package com.altoservicios.centros.presentation.screens.client.residence

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.DefaultDropdown
import com.altoservicios.centros.presentation.components.dial
import com.altoservicios.centros.presentation.components.openWhatsApp

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ClientResidenceScreen(
    vm: ClientResidenceViewModel = hiltViewModel()
) {
    val residence = vm.residence
    val successMessage = stringResource(R.string.toast_update_my_residence)

    MessageEffect(message = vm.message, onConsumed = { vm.message = "" })

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                DefaultTextField(
                    label = stringResource(R.string.label_name),
                    value = residence.name.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(name = it)) }
                )
                Spacer(modifier = Modifier.height(6.dp))
                DefaultTextField(
                    label = stringResource(R.string.lbl_email),
                    value = residence.email.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(email = it)) },
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(6.dp))
                DefaultTextField(
                    label = stringResource(R.string.label_web),
                    value = residence.web.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(web = it)) },
                    keyboardType = KeyboardType.Uri
                )
                Spacer(modifier = Modifier.height(6.dp))
                DefaultTextField(
                    label = stringResource(R.string.label_phone),
                    value = residence.phone.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(phone = it)) },
                    keyboardType = KeyboardType.Phone
                )
                Spacer(modifier = Modifier.height(6.dp))
                DefaultTextField(
                    label = stringResource(R.string.label_address),
                    value = residence.address.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(address = it)) }
                )
                Spacer(modifier = Modifier.height(6.dp))
                DefaultDropdown(
                    label = stringResource(R.string.label_province),
                    emptyLabel = stringResource(R.string.spinner_province),
                    options = vm.provinces.map { it.id to it.province.orEmpty() },
                    selectedId = residence.provinceId?.toIntOrNull(),
                    onSelected = vm::onProvinceSelected
                )
                Spacer(modifier = Modifier.height(6.dp))
                DefaultDropdown(
                    label = stringResource(R.string.label_town),
                    emptyLabel = stringResource(R.string.spinner_town),
                    options = vm.towns.map { it.id to it.town.orEmpty() },
                    selectedId = residence.townId?.toIntOrNull(),
                    onSelected = vm::onTownSelected
                )
                /*
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    DefaultTextField(
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.label_latitude),
                        value = residence.latitude.orEmpty(),
                        onValueChange = { vm.onResidenceChange(residence.copy(latitude = it)) },
                        keyboardType = KeyboardType.Decimal
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DefaultTextField(
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.label_longitude),
                        value = residence.longitude.orEmpty(),
                        onValueChange = { vm.onResidenceChange(residence.copy(longitude = it)) },
                        keyboardType = KeyboardType.Decimal
                    )
                }
                */
                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = stringResource(R.string.btn_save),
                    onClick = { vm.save(successMessage) }
                )
            }

            if (vm.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
