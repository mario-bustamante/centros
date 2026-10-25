package com.altoservicios.centros.presentation.screens.home.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.OptionDropdown
import com.altoservicios.centros.presentation.screens.home.HomeResidencesViewModel

@Composable
fun HomeResidenceSearchScreen(navController: NavHostController, vm: HomeResidencesViewModel) {
    var search by remember { mutableStateOf(vm.search) }

    LaunchedEffect(Unit) {
        vm.loadSearchOptions()
        vm.loadTowns(search.province)
    }

    MessageEffect(message = vm.errorMessage, onConsumed = { vm.errorMessage = "" })

    val fieldModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 12.dp)

    Scaffold(
        topBar = { DefaultTopBar(title = "Campos Búsqueda", upAvailable = true, navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                modifier = fieldModifier,
                value = search.searchFor,
                onValueChange = { search = search.copy(searchFor = it) },
                label = { Text(text = stringResource(R.string.label_search_for)) },
                singleLine = true
            )

            OptionDropdown(
                modifier = fieldModifier,
                label = stringResource(R.string.label_province),
                emptyLabel = stringResource(R.string.spinner_province),
                options = vm.provinces.map { it.id to it.province.orEmpty() },
                selectedId = search.province,
                onSelected = {
                    search = search.copy(province = it, town = null)
                    vm.loadTowns(it)
                }
            )

            OptionDropdown(
                modifier = fieldModifier,
                label = stringResource(R.string.label_town),
                emptyLabel = stringResource(R.string.spinner_town),
                options = vm.towns.map { it.id to it.town.orEmpty() },
                selectedId = search.town,
                onSelected = { search = search.copy(town = it) }
            )

            OptionDropdown(
                modifier = fieldModifier,
                label = stringResource(R.string.label_price),
                emptyLabel = stringResource(R.string.spinner_price),
                options = vm.prices.map { it.id to it.price.orEmpty() },
                selectedId = search.price,
                onSelected = { search = search.copy(price = it) }
            )

            OptionDropdown(
                modifier = fieldModifier,
                label = stringResource(R.string.label_room),
                emptyLabel = stringResource(R.string.spinner_room),
                options = vm.rooms.map { it.id to it.room.orEmpty() },
                selectedId = search.room,
                onSelected = { search = search.copy(room = it) }
            )

            OptionDropdown(
                modifier = fieldModifier,
                label = stringResource(R.string.label_sector),
                emptyLabel = stringResource(R.string.spinner_sector),
                options = vm.sectors.map { it.id to it.sector.orEmpty() },
                selectedId = search.sector,
                onSelected = { search = search.copy(sector = it) }
            )

            OptionDropdown(
                modifier = fieldModifier,
                label = stringResource(R.string.label_dependence),
                emptyLabel = stringResource(R.string.spinner_dependence),
                options = vm.dependencies.map { it.id to it.dependence.orEmpty() },
                selectedId = search.dependence,
                onSelected = { search = search.copy(dependence = it) }
            )

            Row(
                modifier = fieldModifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.weight(1f), text = stringResource(R.string.label_near))
                Switch(
                    checked = search.near,
                    onCheckedChange = { search = search.copy(near = it) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            DefaultButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.btn_search),
                onClick = {
                    vm.applySearch(search)
                    navController.popBackStack()
                }
            )
        }
    }
}
