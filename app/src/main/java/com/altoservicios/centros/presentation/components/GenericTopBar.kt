package com.altoservicios.centros.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.altoservicios.centros.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericTopBar(
    title: String? = "",
) {
    TopAppBar(
        title = {
            Text( text = title ?: "" )
        },
        navigationIcon = {
            Column(
                modifier = Modifier.padding(start = 10.dp, end = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_h),
                    contentDescription = "Logo",
                    modifier = Modifier.size(150.dp)
                )
            }
        }
    )
}
