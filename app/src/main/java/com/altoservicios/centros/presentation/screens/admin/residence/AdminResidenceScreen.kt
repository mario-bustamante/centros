package com.altoservicios.centros.presentation.screens.admin.residence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.FormTextField
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.OptionDropdown

@Composable
fun AdminResidenceScreen(vm: AdminResidenceViewModel = hiltViewModel()) {
    val residence = vm.residence
    val successMessage = stringResource(R.string.toast_update_my_residence)

    MessageEffect(message = vm.message, onConsumed = { vm.message = "" })

    Scaffold(
        topBar = { DefaultTopBar(title = "Mi Residencia") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                FormTextField(
                    label = stringResource(R.string.label_name),
                    value = residence.name.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(name = it)) }
                )
                FormTextField(
                    label = stringResource(R.string.lbl_email),
                    value = residence.email.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(email = it)) },
                    keyboardType = KeyboardType.Email
                )
                FormTextField(
                    label = stringResource(R.string.label_web),
                    value = residence.web.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(web = it)) },
                    keyboardType = KeyboardType.Uri
                )
                FormTextField(
                    label = stringResource(R.string.label_price),
                    value = residence.price.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(price = it)) },
                    keyboardType = KeyboardType.Number
                )
                FormTextField(
                    label = stringResource(R.string.label_phone),
                    value = residence.phone.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(phone = it)) },
                    keyboardType = KeyboardType.Phone
                )
                FormTextField(
                    label = stringResource(R.string.label_address),
                    value = residence.address.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(address = it)) }
                )

                OptionDropdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    label = stringResource(R.string.label_province),
                    emptyLabel = stringResource(R.string.spinner_province),
                    options = vm.provinces.map { it.id to it.province.orEmpty() },
                    selectedId = residence.provinceId?.toIntOrNull(),
                    onSelected = vm::onProvinceSelected
                )
                OptionDropdown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    label = stringResource(R.string.label_town),
                    emptyLabel = stringResource(R.string.spinner_town),
                    options = vm.towns.map { it.id to it.town.orEmpty() },
                    selectedId = residence.townId?.toIntOrNull(),
                    onSelected = vm::onTownSelected
                )

                FormTextField(
                    label = stringResource(R.string.label_description),
                    value = residence.description.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(description = it)) },
                    singleLine = false
                )
                FormTextField(
                    label = stringResource(R.string.label_latitude),
                    value = residence.latitude.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(latitude = it)) },
                    keyboardType = KeyboardType.Decimal
                )
                FormTextField(
                    label = stringResource(R.string.label_longitude),
                    value = residence.longitude.orEmpty(),
                    onValueChange = { vm.onResidenceChange(residence.copy(longitude = it)) },
                    keyboardType = KeyboardType.Decimal
                )

                CheckboxGroup(
                    title = stringResource(R.string.label_room),
                    items = vm.rooms.map { it.id to it.room.orEmpty() },
                    selected = vm.selectedRooms,
                    onToggle = vm::toggleRoom
                )
                CheckboxGroup(
                    title = stringResource(R.string.label_sector),
                    items = vm.sectors.map { it.id to it.sector.orEmpty() },
                    selected = vm.selectedSectors,
                    onToggle = vm::toggleSector
                )
                CheckboxGroup(
                    title = stringResource(R.string.label_dependence),
                    items = vm.dependencies.map { it.id to it.dependence.orEmpty() },
                    selected = vm.selectedDependencies,
                    onToggle = vm::toggleDependence
                )

                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
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

@Composable
private fun CheckboxGroup(
    title: String,
    items: List<Pair<Int, String>>,
    selected: Set<Int>,
    onToggle: (Int) -> Unit
) {
    if (items.isEmpty()) return

    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = title,
        fontWeight = FontWeight.Bold,
        color = colorResource(R.color.colorPrimary)
    )
    items.forEach { (id, text) ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = id in selected, onCheckedChange = { onToggle(id) })
            Text(text = text)
        }
    }
}
