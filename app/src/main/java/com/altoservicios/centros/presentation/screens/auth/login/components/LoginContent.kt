package com.altoservicios.centros.presentation.screens.auth.login.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.navigation.screen.AuthScreen
import com.altoservicios.centros.presentation.screens.auth.login.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LoginContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    vm: LoginViewModel = hiltViewModel()
) {

    val state = vm.state
    val context = LocalContext.current
    val googleError = stringResource(R.string.msg_google_error)

    val googleClient = remember {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, options)
    }

    val googleLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Si el usuario cancela, no se muestra ningun error
        if (result.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                .getResult(ApiException::class.java)
            vm.loginWithGoogle(account.idToken)
        } catch (e: ApiException) {
            vm.onGoogleError(googleError)
        }
    }

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

    Box {
        Column(
            modifier = Modifier
                .background(color = colorResource(id = R.color.colorBgSecondary))
                .fillMaxSize()
                .padding(10.dp, 10.dp, 10.dp, 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!logoIsVertical) {
                Image(
                    modifier = Modifier
                        .height((configuration.screenHeightDp * 0.30f).dp)
                        .padding(all = 10.dp),
                    painter = painterResource(id = logoResource),
                    contentDescription = "Image",
                    contentScale = ContentScale.Fit
                )
            }

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
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = "Ingresar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )


                    Button(
                        { googleClient.signOut().addOnCompleteListener { googleLauncher.launch(googleClient.signInIntent) } },
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
                        onClick = {
                            onLogin(vm)
                        },
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

private fun onLogin(vm: LoginViewModel) {
    vm.login()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Scaffold() { paddingValues ->
        LoginContent(rememberNavController(), paddingValues)
    }
}