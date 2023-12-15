package com.example.facetimeclonecompose.presentation.loginScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import ir.kaaveh.sdpcompose.sdp
import com.example.facetimeclonecompose.presentation.loginScreen.LoginViewModel.UiEvent
import com.example.facetimeclonecompose.presentation.loginScreen.components.TransparentInputField
import com.example.facetimeclonecompose.presentation.loginScreen.uiStates.LoginUiEvent
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import com.example.facetimeclonecompose.presentation.ui.theme.BorderGray
import com.example.facetimeclonecompose.presentation.ui.theme.GrayText
import com.example.facetimeclonecompose.presentation.ui.theme.LightButtonColor
import com.example.facetimeclonecompose.presentation.ui.theme.TextHint
import com.example.facetimeclonecompose.presentation.utilities.Screen
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowMessage -> snackbarHostState.showSnackbar(event.error)
                UiEvent.LoginSuccess -> navController.navigate(Screen.HomeScreen.route)
                UiEvent.VerifyAccount -> navController.navigate(Screen.HomeScreen.route)//TODO("EDIT")
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = DarkGray,
        contentColor = DarkGray
    ) {
        if (viewModel.loginUiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .padding(vertical = 40.sdp, horizontal = 25.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FaceTime",
                    fontSize = 24.ssp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.size(14.sdp))
                Text(
                    text = "Sign in with your email and password to active FaceTime",
                    lineHeight = 25.ssp,
                    fontSize = 14.ssp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.size(10.sdp))
                Text(
                    text = "Learn more about FaceTime",
                    lineHeight = 25.ssp,
                    fontSize = 14.ssp,
                    color = GrayText,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.size(30.sdp))
                Card(
                    border = BorderStroke(1.sdp, BorderGray),
                    shape = RoundedCornerShape(7.sdp)
                ) {
                    TransparentInputField(
                        text = viewModel.loginUiState.emailUiState.text,
                        leadingText = "Email",
                        hint = "example@gmail.com",
                        onValueChange = {
                            viewModel.onEvent(LoginUiEvent.EmailChanged(it))
                        },
                        keyboardType = KeyboardType.Email,
                        isErrorVisible = viewModel.loginUiState.emailUiState.errorMessage != null,
                        error = viewModel.loginUiState.emailUiState.errorMessage
                    )
                    Divider(color = BorderGray, thickness = 1.dp)
                    TransparentInputField(
                        text = viewModel.loginUiState.passwordUiState.text,
                        leadingText = "Password",
                        hint = "Example123",
                        onValueChange = {
                            viewModel.onEvent(LoginUiEvent.PasswordChanged(it))
                        },
                        keyboardType = KeyboardType.Password,
                        isErrorVisible = viewModel.loginUiState.passwordUiState.errorMessage != null,
                        error = viewModel.loginUiState.passwordUiState.errorMessage,
                        textVisible = false
                    )
                }
                Spacer(Modifier.size(20.sdp))
                Button(
                    onClick = { viewModel.login() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightButtonColor,
                        contentColor = LightButtonColor
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 45.sdp
                    ),
                    shape = RoundedCornerShape(5.sdp)
                ) {
                    Text(text = "Sign In", fontSize = 13.ssp, color = DarkGray)
                }
                Spacer(Modifier.size(20.sdp))
                TextButton(onClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                }) {
                    Text(
                        text = "create new account",
                        fontSize = 14.ssp,
                        color = GrayText
                    )
                }
            }
        }
    }

}