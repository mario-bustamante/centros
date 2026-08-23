package com.altoservicios.centros.presentation.screens.auth.register.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.navigation.screen.AuthScreen

@Composable
fun RegisterContent(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConfirmation by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier
        .background(color = colorResource(id = R.color.colorBgSecondary))
        .fillMaxSize()
        .padding(paddingValues)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 20.dp),
                painter = painterResource(id = R.drawable.ic_logo_h),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                shape = RoundedCornerShape(
                    topEnd = 30.dp,
                    topStart = 30.dp,
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = "Registrarse",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                        value = email,
                        onValueChange = { email = it },
                        label = "Nombre",
                        icon = Icons.Default.Person
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                        value = email,
                        onValueChange = { email = it },
                        label = "Correo Electronico",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email,
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                        value = email,
                        onValueChange = { email = it },
                        label = "Teléfono",
                        icon = Icons.Default.Phone,
                        keyboardType = KeyboardType.Number,
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        onValueChange = { password = it },
                        label = "Confirmar Password",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                    )

                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(top = 20.dp),
                        onClick = { /*onLogin(email.trim(), password) */ },
                        text = "Confirmar"
                    )
                }
            }

        }

    }
}

private fun onRegister(navController: NavHostController) {
    navController.navigate(route = AuthScreen.Register.route)
}

private fun signInGoogle() {

}

private fun login(email: String, password: String) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Scaffold() { paddingValues ->
        RegisterContent(rememberNavController(), paddingValues)
    }
}