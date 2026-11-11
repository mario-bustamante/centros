package com.altoservicios.centros.presentation.screens.residences.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.dial
import com.altoservicios.centros.presentation.components.imageUrl
import com.altoservicios.centros.presentation.components.openWhatsApp
import com.altoservicios.centros.presentation.components.priceLabel
import com.altoservicios.centros.presentation.screens.auth.register.RegisterScreen
import androidx.core.net.toUri

@Composable
fun ResidenceDetailScreen(
    navController: NavHostController,
    residence: Residence
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Residencia",
                upAvailable = true,
                navController = navController)
        },
        bottomBar = {
            Row(modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 25.dp)
            ) {
                DefaultButton(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(R.string.btn_call),
                    icon = Icons.Default.Phone,
                    onClick = { context.dial(residence.phone) }
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                DefaultButton(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(R.string.btn_whatsapp),
                    painter = painterResource(id = R.drawable.ic_whatsapp),
                    onClick = { context.openWhatsApp(residence.phone) },
                )

            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
                .verticalScroll(rememberScrollState())
                .padding(10.dp)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = residence.name.orEmpty(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )

                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = listOfNotNull(
                            residence.address,
                            residence.town,
                            residence.province
                        )
                            .filter { it.isNotBlank() }
                            .joinToString(", ")
                    )
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))
            imageUrl(residence.imageUrl)?.let { url ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = imageUrl(residence.imageUrl) ?: R.drawable.logo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = priceLabel(residence.price)
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = HtmlCompat.fromHtml(
                            residence.description.orEmpty(),
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString().trim()
                    )
                }
            }

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                text = stringResource(R.string.btn_map),
                icon = Icons.Default.Place,
                onClick = {
                    // 1. Create the geo URI string with coordinates and a label query
                    val latitude = residence.latitude
                    val longitude = residence.longitude
                    val label =
                        Uri.encode(residence.name) // Optional: replaces the raw coordinates with text on the map pin

                    val mapUri =
                        "geo:$latitude,$longitude?q=$latitude,$longitude($label)".toUri()

                    // 2. Create the Intent to view the map
                    val mapIntent = Intent(Intent.ACTION_VIEW, mapUri).apply {
                        // Force it to use the Google Maps app package if available
                        setPackage("com.google.android.apps.maps")
                    }

                    // 3. Start the activity safely
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        // Fallback: If Google Maps app is missing, launch without forcing the package
                        // (this will open the browser or any other map app available)
                        val fallbackIntent = Intent(Intent.ACTION_VIEW, mapUri)
                        context.startActivity(fallbackIntent)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ResidenceDetailScreen(
        rememberNavController(),
        Residence(
            id = "1",
            name = "residencia nombre",
            email = "mario@gmail.com",
            web = "https://www.residenciasysalud.es",
            price = "1000",
            phone = "123456789",
            description = "description description description description",
            address = "calle 123",
            province = "Granada",
            town = "Granada",
            latitude = "123",
            longitude = "5623",
            //imageUrl = "http://localhost:8000/img/residencia/1/residencia-prueba-02-510x510.webp"
        )
    )
}