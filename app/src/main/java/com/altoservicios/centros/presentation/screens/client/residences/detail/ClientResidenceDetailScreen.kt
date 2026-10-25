package com.altoservicios.centros.presentation.screens.client.residences.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.dial
import com.altoservicios.centros.presentation.components.imageUrl
import com.altoservicios.centros.presentation.components.openWhatsApp
import com.altoservicios.centros.presentation.components.priceLabel
import com.altoservicios.centros.presentation.navigation.screen.ClientRoute

@Composable
fun ClientResidenceDetailScreen(navController: NavHostController, residence: Residence) {
    val context = LocalContext.current

    Scaffold(
        topBar = { DefaultTopBar(title = "Residencia", upAvailable = true, navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            imageUrl(residence.imageUrl)?.let { url ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = residence.name.orEmpty(),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Text(modifier = Modifier.padding(top = 4.dp), text = priceLabel(residence.price))
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = listOfNotNull(residence.address, residence.town, residence.province)
                    .filter { it.isNotBlank() }
                    .joinToString(", ")
            )
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = HtmlCompat.fromHtml(
                    residence.description.orEmpty(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString().trim()
            )

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                text = stringResource(R.string.btn_map),
                onClick = { navController.navigate(ClientRoute.residenceMap(residence)) }
            )

            Row(modifier = Modifier.padding(top = 8.dp)) {
                DefaultButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    text = stringResource(R.string.btn_call),
                    onClick = { context.dial(residence.phone) }
                )
                DefaultButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    text = stringResource(R.string.btn_whatsapp),
                    onClick = { context.openWhatsApp(residence.phone) }
                )
            }
        }
    }
}
