package com.altoservicios.centros.presentation.screens.home.auth.register.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.screens.home.auth.register.RegisterViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun RegisterContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    vm: RegisterViewModel = hiltViewModel()
) {
    val state = vm.state
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val logoResource = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        R.drawable.ic_logo_h
    } else {
        R.drawable.ic_logo_v
    }
    val logoIsVertical = logoResource == R.drawable.ic_logo_h

    LaunchedEffect(key1 = vm.errorMessage) {
        if(vm.errorMessage != "") {
            Toast.makeText(context, vm.errorMessage, Toast.LENGTH_LONG).show()
            vm.errorMessage = ""
        }
    }


    Box(
        modifier = Modifier.padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .background(color = colorResource(id = R.color.colorBgSecondary))
                .fillMaxSize()
                .padding(20.dp, 20.dp, 20.dp, 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!logoIsVertical) {
                Image(
                    modifier = Modifier
                        .height((configuration.screenHeightDp * 0.25f).dp)
                        .padding(all = 20.dp),
                    painter = painterResource(id = logoResource),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop
                )
            }

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
                        value = state.name,
                        onValueChange = { text ->
                           vm.onNameInput(text)
                        },
                        label = "Nombre",
                        icon = Icons.Default.Person
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                        value = state.email,
                        onValueChange = { text ->
                            vm.onEmailInput(text)
                        },
                        label = "Correo Electronico",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email,
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                        value = state.phone,
                        onValueChange = { text ->
                            vm.onPhoneInput(text)
                        },
                        label = "Teléfono",
                        icon = Icons.Default.Phone,
                        keyboardType = KeyboardType.Number,
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        onValueChange = { text ->
                            vm.onPasswordInput(text)
                        },
                        label = "Password",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        hideText = true
                    )

                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.confirmPassword,
                        onValueChange = { text ->
                            vm.onConfirmPasswordInput(text)
                        },
                        label = "Confirmar Password",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        hideText = true
                    )

                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(top = 20.dp),
                        onClick = {
                            onRegister(vm)
                        },
                        text = "Confirmar"
                    )
                }
            }

        }

    }
}

private fun onRegister(vm: RegisterViewModel) {
    vm.register()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Scaffold() { paddingValues ->
        RegisterContent(rememberNavController(), paddingValues)
    }
}