package com.example.facetimeclonecompose

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.util.Consumer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.facetimeclonecompose.presentation.createRoomScreen.CreateRoomScreen
import com.example.facetimeclonecompose.presentation.homeScreen.HomeScreen
import com.example.facetimeclonecompose.presentation.incomingCallScreen.IncomingCallScreen
import com.example.facetimeclonecompose.presentation.loginScreen.LoginScreen
import com.example.facetimeclonecompose.presentation.otpScreen.OtpCodeScreen
import com.example.facetimeclonecompose.presentation.registerScreen.RegisterScreen
import com.example.facetimeclonecompose.presentation.splashScreen.SplashViewModel
import com.example.facetimeclonecompose.presentation.ui.theme.FaceTimeCloneComposeTheme
import com.example.facetimeclonecompose.presentation.utilities.Constants.DEEP_LINK_BASE_URL
import com.example.facetimeclonecompose.presentation.utilities.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplashScreen()
        handleNotificationPermission()
        setContent {
            window.statusBarColor = getColor(R.color.dark_gray)
            window.navigationBarColor = getColor(R.color.dark_gray)
            val navController = rememberNavController()
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                Log.d("TAGTAGTAGTAG", "${destination}: third state")
            }
            DisposableEffect(Unit) {
                val listener = Consumer<Intent> { intent ->
                    intent.data?.let {
                        splashViewModel.handleDeeplink(it)
                    }
                }
                addOnNewIntentListener(listener)
                onDispose { removeOnNewIntentListener(listener) }
            }
            LaunchedEffect(key1 = intent) {
                splashViewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is SplashViewModel.UiEvent.IncomingCall -> {
                            val deepLink = NavDeepLinkRequest.Builder.fromUri(event.uri).build()
                            val options = NavOptions.Builder().setPopUpTo(navController.graph.startDestinationRoute, true).build()
                            navController.navigate(deepLink, options)
                            intent = null // cold deeplink
                        }
                    }
                }
            }

            FaceTimeCloneComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = splashViewModel.isLoadingState.startDestination
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
                        composable(route = Screen.OtpCodeScreen.route) {
                            OtpCodeScreen(
                                navController = navController
                            )
                        }
                        composable(
                            route = Screen.IncomingCallScreen.route,
                            deepLinks = listOf(navDeepLink {
                                uriPattern =
                                    "$DEEP_LINK_BASE_URL/callId={id}/callAuthor={author}/roomType={callType}"
                            }
                            ),
                            arguments = listOf(navArgument("id") {
                                type = NavType.StringType
                            }, navArgument("author") {
                                type = NavType.StringType
                            }, navArgument("callType") {
                                type = NavType.StringType
                            })
                        ) {
                            IncomingCallScreen( navController = navController,
                                roomId = it.arguments?.getString("id").toString(),
                                authorName = it.arguments?.getString("author").toString(),
                                roomType = it.arguments?.getString("callType").toString()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashViewModel.isLoadingState.isLoading
            }
        }
    }

    private fun handleNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val pushNotificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}
            pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onStart() { //TODO("CHECK DEEP LINK WITHOUT THAT")
        super.onStart()
        intent?.data?.let { splashViewModel.handleDeeplink(it)}
    }
}