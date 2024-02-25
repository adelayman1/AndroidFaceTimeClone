package com.example.facetimeclonecompose.presentation.otpScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.facetimeclonecompose.presentation.otpScreen.components.OtpCodeView
import com.example.facetimeclonecompose.presentation.otpScreen.uiStates.OtpUiEvent
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import com.example.facetimeclonecompose.presentation.ui.theme.LightButtonColor
import com.example.facetimeclonecompose.presentation.ui.theme.Red
import com.example.facetimeclonecompose.presentation.ui.theme.UbuntuFont
import com.example.facetimeclonecompose.presentation.utilities.Screen
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun OtpCodeScreen(
    navController: NavController,
    viewModel: OtpVIewModel = hiltViewModel()
) {
    val focusRequest = FocusRequester()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                OtpVIewModel.UiEvent.OtpVerifiedSuccessfully -> {
                    navController.navigate(Screen.HomeScreen.route){
                        popUpTo(0)
                    }
                }
                is OtpVIewModel.UiEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }
    Scaffold(
        containerColor = DarkGray,
        contentColor = DarkGray,
        modifier = Modifier
            .background(DarkGray)
            .padding(vertical = 20.sdp, horizontal = 15.sdp),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    )
    {
        if(viewModel.otpUiState.isLoading){
            CircularProgressIndicator()
        }else{
            Column(verticalArrangement = Arrangement.Center) {

                Spacer(modifier = Modifier.size(35.sdp))
                Text(
                    modifier = Modifier
                        .padding(top = 3.sdp)
                        .fillMaxWidth(),
                    text = " CO \n \n \nDE",
                    textAlign = TextAlign.Center,
                    fontFamily = UbuntuFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.ssp,
                    letterSpacing = TextUnit(0.3F, TextUnitType.Sp),
                    color = LightButtonColor
                )
                Text(
                    modifier = Modifier
                        .padding(top = 3.sdp)
                        .fillMaxWidth(),
                    text = "VERIFICATION",
                    textAlign = TextAlign.Center,
                    fontFamily = UbuntuFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.ssp,
                    color = LightButtonColor
                )

                Spacer(modifier = Modifier.size(25.sdp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Enter the verification code sent on Email at \n" +
                            "adelayman0000@gmail.com",
                    textAlign = TextAlign.Center,
                    fontFamily = UbuntuFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.ssp,
                    color = LightButtonColor
                )
                OtpCodeView(
                    state = viewModel.otpUiState,
                    onTextChanged = {
                        viewModel.onEvent(OtpUiEvent.OtpCodeChanged(it))
                    },
                    focusRequester = focusRequest
                )
                Spacer(modifier = Modifier.size(50.sdp))
                Button(
                    onClick = {
                              viewModel.onEvent(OtpUiEvent.VerifyOTP)
                    },
                    modifier = Modifier
                        .padding(horizontal = 10.sdp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightButtonColor,
                        contentColor = LightButtonColor
                    ),

                    contentPadding = PaddingValues(
                        horizontal = 45.sdp
                    ),
                    shape = RoundedCornerShape(5.sdp)
                ) {
                    Text(text = "Verify", fontSize = 13.ssp, color = DarkGray)
                }
                Spacer(modifier = Modifier.size(50.sdp))
            }
        }

    }
    // TODO("ERROR ANIMATION CODE DUPLICATED")
}