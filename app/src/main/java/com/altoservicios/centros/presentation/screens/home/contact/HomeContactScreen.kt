package com.altoservicios.centros.presentation.screens.home.contact

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.screens.home.contact.components.HomeContactContent

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeContactScreen(
    navController: NavHostController,
    vm: HomeContactViewModel = hiltViewModel()
) {
    Scaffold { paddingValues ->
        HomeContactContent(vm)
    }
}
