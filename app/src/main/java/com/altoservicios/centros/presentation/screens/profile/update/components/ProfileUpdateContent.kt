package com.altoservicios.centros.presentation.screens.profile.update.components

import android.content.Intent

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.screens.MainActivity
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel
import com.altoservicios.centros.presentation.screens.profile.update.ProfileUpdateViewModel

@Composable
fun ProfileUpdateContent(paddingValues: PaddingValues, vm: ProfileUpdateViewModel = hiltViewModel()) {

    val activity = LocalActivity.current

    Box(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .padding(bottom = 55.dp)
            .fillMaxSize()
            .background(colorResource(R.color.colorBgSecondary))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            if(!vm.user?.image.isNullOrBlank()) {
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .border(2.dp, Color.Gray, CircleShape)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally),
                    model = vm.user?.image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

            } else {
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .border(2.dp, Color.Gray, CircleShape)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.user_image),
                    contentDescription = "",
                )
            }






            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topEnd = 40.dp,
                    topStart = 40.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.7f)
                )
            ) {


                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DefaultTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "",
                            onValueChange = {},
                            label = "Nombre",
                            icon = Icons.Default.Person
                        )

                        DefaultTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "",
                            onValueChange = {},
                            label = "Correo",
                            icon = Icons.Default.Email
                        )

                        DefaultTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "",
                            onValueChange = {},
                            label = "Telefono",
                            icon = Icons.Default.Phone
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    DefaultButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Confirmar",
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileUpdateContentPreview() {
    Scaffold() { paddingValues ->
        ProfileUpdateContent(paddingValues)
    }
}