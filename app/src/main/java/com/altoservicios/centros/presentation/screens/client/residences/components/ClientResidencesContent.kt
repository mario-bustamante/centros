package com.altoservicios.centros.presentation.screens.client.residences.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.components.imageUrl
import com.altoservicios.centros.presentation.components.priceLabel
import com.altoservicios.centros.presentation.screens.residences.list.components.ResidenceItem

@Composable
fun ClientResidencesContent(
    paddingValues: PaddingValues,
    residences: List<Residence>,
    isLoading: Boolean,
    errorMessage: String,
    onErrorConsumed: () -> Unit,
    onLoadNextPage: () -> Unit,
    onResidenceClick: (Residence) -> Unit
) {
    val listState = rememberLazyListState()

    MessageEffect(message = errorMessage, onConsumed = onErrorConsumed)

    // Carga la siguiente pagina al llegar a los ultimos elementos
    LaunchedEffect(listState, residences.size) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisible ->
                if (lastVisible != null && lastVisible >= residences.size - 3) {
                    onLoadNextPage()
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.colorBgSecondary))
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(items = residences, key = { it.id }) { residence ->
                ClientResidenceItem(residence = residence, onClick = { onResidenceClick(residence) })
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp))
        } else if (residences.isEmpty()) {
            Text(modifier = Modifier.align(Alignment.Center), text = "No se encontraron residencias")
        }
    }
}
