package com.altoservicios.centros.presentation.screens.admin.rooms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.UserHomeTopBar
import com.altoservicios.centros.presentation.navigation.screen.AdminScreen
import com.altoservicios.centros.presentation.screens.admin.residence.AdminResidenceViewModel
import com.altoservicios.centros.presentation.screens.admin.rooms.components.CheckboxGroup
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel

@Composable
fun AdminRoomsScreen(
    vm: AdminResidenceViewModel,
    navController: NavHostController,
    onLogout: () -> Unit = {}
) {
    val successMessage = stringResource(R.string.toast_update_my_residence)
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val screens = listOf(
        AdminScreen.Residence,
        AdminScreen.Rooms,
        AdminScreen.Photos,
        AdminScreen.Profile
    )

    MessageEffect(message = vm.message, onConsumed = { vm.message = "" })

    UserHomeTopBar(
        title = "Habitaciones",
        drawerState = drawerState,
        screens = screens,
        navController = navController,
        onLogout = { profileViewModel.logout(onLogout) },
        content = {
            Scaffold { paddingValues ->
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
                        CheckboxGroup(
                            title = stringResource(R.string.label_room),
                            items = vm.rooms.map { it.id to it.room.orEmpty()  },
                            selected = vm.selectedRooms,
                            onToggle = vm::toggleRoom,
                            quantities = vm.rooms.associate { it.id to (it.numAvailable ?: 0).toString() }
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
    )
}
