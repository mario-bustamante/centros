package com.altoservicios.centros.presentation.screens.admin.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.imageUrl
import com.altoservicios.centros.presentation.navigation.screen.AdminRoute

@Composable
fun AdminPhotosScreen(navController: NavHostController, vm: AdminPhotosViewModel = hiltViewModel()) {
    // Se recarga cada vez que se vuelve de editar una fotografia
    LaunchedEffect(Unit) { vm.load() }

    MessageEffect(message = vm.message, onConsumed = { vm.message = "" })

    Scaffold(
        topBar = { DefaultTopBar(title = "Mis Fotografías") },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AdminRoute.photo(null)) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar fotografía")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(items = vm.photos, key = { it.id ?: it.hashCode() }) { photo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .clickable { navController.navigate(AdminRoute.photo(photo)) },
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                    ) {
                        Row(modifier = Modifier.padding(10.dp)) {
                            AsyncImage(
                                modifier = Modifier.size(width = 110.dp, height = 80.dp),
                                model = imageUrl(photo.path),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.padding(start = 10.dp)) {
                                Text(
                                    text = photo.title.orEmpty(),
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = photo.description.orEmpty(),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (photo.principal == 1) {
                                    Text(text = "Principal", color = colorResource(R.color.colorLink))
                                }
                            }
                        }
                    }
                }
            }

            if (vm.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (vm.photos.isEmpty()) {
                Text(modifier = Modifier.align(Alignment.Center), text = "No hay fotografías")
            }
        }
    }
}
