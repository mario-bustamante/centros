package com.altoservicios.centros.presentation.screens.auth.login.components

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.res.Configuration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.navigation.screen.AuthScreen
import com.altoservicios.centros.presentation.screens.auth.login.LoginViewModel
import com.altoservicios.centros.presentation.screens.auth.register.RegisterScreen

@Composable
fun LoginContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    vm: LoginViewModel = hiltViewModel()
) {

    val state = vm.state

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val logoResource = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        R.drawable.ic_logo_h
    } else {
        R.drawable.ic_logo_v
    }
    val logoIsVertical = logoResource == R.drawable.ic_logo_h

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(color = colorResource(id = R.color.colorBgSecondary))
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.double_space)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (logoIsVertical) {
                            Modifier.height((configuration.screenHeightDp * 0.3f).dp)
                        } else {
                            Modifier
                        }
                    )
                    .padding(all = 20.dp),
                painter = painterResource(id = logoResource),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
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
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = "Ingresar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )


                    Button(
                        { /*onGoogleLogin()*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.btn_margin),
                                end = dimensionResource(id = R.dimen.btn_margin)
                            )
                    ) {
                        Text(text = stringResource(id = R.string.btn_google_login))
                    }


                    Text(
                        text = stringResource(id = R.string.lbl_option_login),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        textAlign = TextAlign.Center
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

                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(top = 20.dp),
                        onClick = { /*onLogin(email.trim(), password) */ },
                        text = stringResource(id = R.string.btn_login)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 10.dp),
                            text = stringResource(id = R.string.lbl_register)
                        )

                        Text(
                            text = "Registrate",
                            modifier = Modifier
                                .clickable { onRegister(navController) },
                            color = Color.Blue,
                        )
                    }
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
        LoginContent(rememberNavController(), paddingValues)
    }
}