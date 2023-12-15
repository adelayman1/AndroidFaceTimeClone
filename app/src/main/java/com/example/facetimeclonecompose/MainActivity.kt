package com.example.facetimeclonecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.facetimeclonecompose.presentation.createRoomScreen.CreateRoomScreen
import com.example.facetimeclonecompose.presentation.homeScreen.HomeScreen
import com.example.facetimeclonecompose.presentation.loginScreen.LoginScreen
import com.example.facetimeclonecompose.presentation.registerScreen.RegisterScreen
import com.example.facetimeclonecompose.presentation.ui.theme.FaceTimeCloneComposeTheme
import com.example.facetimeclonecompose.presentation.utilities.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FaceTimeCloneComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = Screen.CreateRoomScreen.route
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.RegisterScreen.route) {
                            RegisterScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.CreateRoomScreen.route) {
                            CreateRoomScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}