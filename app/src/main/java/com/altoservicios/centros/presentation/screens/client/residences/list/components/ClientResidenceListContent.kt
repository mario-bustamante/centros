package com.altoservicios.centros.presentation.screens.client.residences.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.components.MessageEffect
import com.altoservicios.centros.presentation.screens.client.residences.ClientResidencesViewModel

@Composable
fun ClientResidenceListContent(
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
            .padding(paddingValues)
            .fillMaxSize()
            .background(colorResource(R.color.colorBgSecondary))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(items = residences, key = { it.id }) { residence ->
                ResidenceItem(residence = residence, onClick = { onResidenceClick(residence) })
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp))
        } else if (residences.isEmpty()) {
            Text(modifier = Modifier.align(Alignment.Center), text = "No se encontraron residencias")
        }
    }
}
