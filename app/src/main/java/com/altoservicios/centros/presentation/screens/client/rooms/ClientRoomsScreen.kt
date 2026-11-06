package com.altoservicios.centros.presentation.screens.client.rooms

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.screens.client.residence.ClientResidenceViewModel
import com.altoservicios.centros.presentation.screens.client.rooms.components.ListRooms

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ClientRoomsScreen(
    vm: ClientResidenceViewModel = hiltViewModel()
) {
    val successMessage = stringResource(R.string.toast_update_my_rooms)
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
                ListRooms(
                    title = stringResource(R.string.label_room),
                    items = vm.rooms.map { it.id to it.room.orEmpty()  },
                    quantities = vm.availability,
                    onQuantityChange = vm::onAvailabilityChange
                )

                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = stringResource(R.string.btn_save),
                    onClick = { vm.saveRooms(successMessage) }
                )
            }

            if (vm.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
