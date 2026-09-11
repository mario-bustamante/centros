package com.altoservicios.centros.presentation.screens.admin.category.list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.altoservicios.centros.presentation.screens.admin.category.list.components.AdminCategoryListContent
import com.altoservicios.centros.presentation.screens.client.category.list.components.ClientCategoryListContent

@Composable
fun AdminCategoryListScreen() {
    Scaffold() { paddingValues ->
        AdminCategoryListContent(paddingValues)
    }
}