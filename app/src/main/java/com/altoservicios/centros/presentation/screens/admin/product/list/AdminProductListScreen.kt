package com.altoservicios.centros.presentation.screens.admin.product.list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.altoservicios.centros.presentation.screens.admin.product.list.components.AdminProductListContent
import com.altoservicios.centros.presentation.screens.client.product.list.components.ClientProductListContent

@Composable
fun AdminProductListScreen() {
    Scaffold() { paddingValues ->
        AdminProductListContent(paddingValues)
    }
}