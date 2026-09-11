package com.altoservicios.centros.presentation.screens.client.category.list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.altoservicios.centros.presentation.screens.client.category.list.components.ClientCategoryListContent

@Composable
fun ClientCategoryListScreen() {
    Scaffold() { paddingValues ->
        ClientCategoryListContent(paddingValues)
    }
}