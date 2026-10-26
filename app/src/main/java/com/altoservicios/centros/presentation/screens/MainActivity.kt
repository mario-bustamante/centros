package com.altoservicios.centros.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.navigation.graph.RootNavGraph
import com.altoservicios.centros.ui.theme.CentrosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CentrosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.colorBgSecondary)
                ) {
                    navController = rememberNavController()
                    RootNavGraph(navController = navController)
                }
            }
        }
    }
}

/*



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
}*/