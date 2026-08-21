package com.altoservicios.centros

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import com.altoservicios.centros.auth.RegisterActivity
import com.altoservicios.centros.ui.theme.CentrosTheme
import com.google.android.gms.common.SignInButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CentrosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.colorBgSecondary)
                ) {
                    LoginContent(
                        onLogin = { email, password -> login(email, password) },
                        onRegister = { register() },
                        onGoogleLogin = { signInGoogle() }
                    )
                }
            }
        }
    }
}


private fun register() {


    RegisterActivity()
}

private fun signInGoogle() {

}

private fun login(email: String, password: String) {

}

@Composable
fun LoginContent(
    onLogin: (String, String) -> Unit,
    onRegister: () -> Unit,
    onGoogleLogin: () -> Unit
) {
    var email by rememberSaveable () { mutableStateOf("") }
    var password by rememberSaveable() { mutableStateOf("") }


    Column(modifier = Modifier
        .background(color = colorResource(id = R.color.colorBgSecondary))
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(dimensionResource(R.dimen.double_space)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_logo_h),
            contentDescription = "Image",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.triple_space)))

        AndroidView(
            factory = { context ->
                SignInButton(context).apply {
                    setSize(SignInButton.SIZE_WIDE)
                    setOnClickListener { onGoogleLogin }
                }
            },
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.triple_space)))

        Text(
            text = stringResource(id = R.string.lbl_option_login),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.triple_space)))

        Text(
            text = stringResource(id = R.string.lbl_email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.white)),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.triple_space)))

        Text(
            text = stringResource(id = R.string.lbl_password),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.white)),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.triple_space)))

        Button(
            onClick = { onLogin(email.trim(), password) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.btn_margin),
                    end = dimensionResource(id = R.dimen.btn_margin)
                )
        ) {
            Text(text = stringResource(id = R.string.btn_login))
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.triple_space)))

        Text(
                text = stringResource(id = R.string.lbl_register),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.space))
                    .clickable { (onRegister) },
                textAlign = TextAlign.Center
            )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    CentrosTheme {
        LoginContent(
            onLogin = { email, password -> login(email, password) },
            onRegister = { register() },
            onGoogleLogin = { signInGoogle() }
        )
    }
}