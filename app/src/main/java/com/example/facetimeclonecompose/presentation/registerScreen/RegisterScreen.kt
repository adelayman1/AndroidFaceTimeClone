package com.example.facetimeclonecompose.presentation.registerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.facetimeclonecompose.presentation.loginScreen.components.TransparentInputField
import com.example.facetimeclonecompose.presentation.loginScreen.uiStates.LoginUiEvent
import com.example.facetimeclonecompose.presentation.registerScreen.uiStates.RegisterUiEvent
import com.example.facetimeclonecompose.presentation.ui.theme.BorderGray
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import com.example.facetimeclonecompose.presentation.ui.theme.GrayText
import com.example.facetimeclonecompose.presentation.ui.theme.LightButtonColor
import com.example.facetimeclonecompose.presentation.utilities.Screen
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                RegisterViewModel.UiEvent.RegisterSuccess -> navController.navigate(Screen.HomeScreen.route)//TODO("EDIT")
                is RegisterViewModel.UiEvent.ShowMessage -> snackbarHostState.showSnackbar(event.error)
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = DarkGray,
        contentColor = DarkGray
    ) {
        if (viewModel.registerUiState.isLoading) {
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
                    text = "Sign up with email and password to active FaceTime",
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
                        text = viewModel.registerUiState.nameUiState.text,
                        leadingText = "Name",
                        hint = "example",
                        onValueChange = {
                            viewModel.onEvent(RegisterUiEvent.NameChanged(it))
                        },
                        keyboardType = KeyboardType.Text,
                        isErrorVisible = viewModel.registerUiState.nameUiState.errorMessage != null,
                        error = viewModel.registerUiState.nameUiState.errorMessage
                    )
                    Divider(color = BorderGray, thickness = 1.dp)
                    TransparentInputField(
                        text = viewModel.registerUiState.emailUiState.text,
                        leadingText = "Email",
                        hint = "example@gmail.com",
                        onValueChange = {
                            viewModel.onEvent(RegisterUiEvent.EmailChanged(it))
                        },
                        keyboardType = KeyboardType.Email,
                        isErrorVisible = viewModel.registerUiState.emailUiState.errorMessage != null,
                        error = viewModel.registerUiState.emailUiState.errorMessage
                    )
                    Divider(color = BorderGray, thickness = 1.dp)
                    TransparentInputField(
                        text = viewModel.registerUiState.passwordUiState.text,
                        leadingText = "Password",
                        hint = "Example123",
                        onValueChange = {
                            viewModel.onEvent(RegisterUiEvent.PasswordChanged(it))
                        },
                        keyboardType = KeyboardType.Password,
                        isErrorVisible = viewModel.registerUiState.passwordUiState.errorMessage != null,
                        error = viewModel.registerUiState.passwordUiState.errorMessage,
                        textVisible = false
                    )
                    Divider(color = BorderGray, thickness = 1.dp)
                    TransparentInputField(
                        text = viewModel.registerUiState.confirmPasswordUiState.text,
                        leadingText = "Confirm Password",
                        hint = "Example123",
                        onValueChange = {
                            viewModel.onEvent(RegisterUiEvent.ConfirmPasswordChanged(it))
                        },
                        keyboardType = KeyboardType.Password,
                        isErrorVisible = viewModel.registerUiState.confirmPasswordUiState.errorMessage != null,
                        error = viewModel.registerUiState.confirmPasswordUiState.errorMessage,
                        textVisible = false
                    )
                }
                Spacer(Modifier.size(20.sdp))
                Button(
                    onClick = { viewModel.register() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightButtonColor,
                        contentColor = LightButtonColor
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 45.sdp
                    ),
                    shape = RoundedCornerShape(5.sdp)
                ) {
                    Text(text = "Sign Up", fontSize = 13.ssp, color = DarkGray)
                }
                Spacer(Modifier.size(20.sdp))
                TextButton(onClick = {
//                TODO("EDIT")    var conferenceOptions: JitsiMeetConferenceOptions =
//                        JitsiMeetConferenceOptions.Builder()
//                            .setRoom("https://meet.jit.si/8sljhjjshhd8}")
//                            .setAudioMuted(false)
//                            .setUserInfo(JitsiMeetUserInfo(name))
//                            .setVideoMuted(true)
//                            .setFeatureFlag("invite.enabled", false)
//                            .build()
//                    JitsiMeetActivity.launch(context, conferenceOptions)
                   navController.navigate(Screen.LoginScreen.route)
                }) {
                    Text(
                        text = "have an account",
                        fontSize = 14.ssp,
                        color = GrayText
                    )
                }
            }
        }
    }
}
