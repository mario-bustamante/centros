package com.altoservicios.centros.presentation.screens.residences.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.core.net.toUri
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

@Composable
fun ResidenceDetailScreen(
    navController: NavHostController,
    residence: Residence
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Detalles de la Residencia",
                upAvailable = true,
                navController = navController)
        },
        bottomBar = {
            if (!residence.phone.isNullOrBlank()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp, 16.dp, 16.dp, 20.dp)
                ) {
                    DefaultButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.btn_call),
                        icon = Icons.Default.Phone,
                        onClick = { context.dial(residence.phone) }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    DefaultButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.btn_whatsapp),
                        painter = painterResource(id = R.drawable.ic_whatsapp),
                        onClick = { context.openWhatsApp(residence.phone) },
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colorResource(R.color.colorBgSecondary))
                .verticalScroll(rememberScrollState())
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = residence.name.orEmpty(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                            color = colorResource(R.color.colorPrimary)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = colorResource(R.color.colorPrimary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = listOfNotNull(
                                    residence.address,
                                    residence.town,
                                    residence.province
                                )
                                    .filter { it.isNotBlank() }
                                    .joinToString(", "),
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    imageUrl(residence.imageUrl)?.let { url ->
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
                            model = imageUrl(residence.imageUrl) ?: R.drawable.logo,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                if (!residence.price.isNullOrBlank() || !residence.description.isNullOrBlank()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {
                            if (!residence.price.isNullOrBlank()) {
                                Text(
                                    text = priceLabel(residence.price),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 20.sp,
                                    color = colorResource(R.color.colorPrimary)
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            Text(
                                text = HtmlCompat.fromHtml(
                                    residence.description.orEmpty(),
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                ).toString().trim(),
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if( !residence.latitude.isNullOrBlank() && !residence.longitude.isNullOrBlank()) {
                    DefaultButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.btn_map),
                        icon = Icons.Default.Place,
                        onClick = {
                            val latitude = residence.latitude
                            val longitude = residence.longitude
                            val label = Uri.encode(residence.name)

                            val mapUri =
                                "geo:$latitude,$longitude?q=$latitude,$longitude($label)".toUri()

                            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri).apply {
                                setPackage("com.google.android.apps.maps")
                            }

                            try {
                                context.startActivity(mapIntent)
                            } catch (e: Exception) {
                                val fallbackIntent = Intent(Intent.ACTION_VIEW, mapUri)
                                context.startActivity(fallbackIntent)
                            }
                        }
                    )
                }
            }
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
            imageUrl = "imagen-no-disponible-residencias-y-salud.webp"
        )
    )
}