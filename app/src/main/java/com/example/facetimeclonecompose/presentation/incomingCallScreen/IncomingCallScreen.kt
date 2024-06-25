package com.example.facetimeclonecompose.presentation.incomingCallScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.CallEnd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import com.example.facetimeclonecompose.presentation.ui.theme.DarkWhite
import com.example.facetimeclonecompose.presentation.ui.theme.Gray30
import com.example.facetimeclonecompose.presentation.ui.theme.GreenLightSecond
import com.example.facetimeclonecompose.presentation.ui.theme.RedLight
import com.example.facetimeclonecompose.presentation.ui.theme.UbuntuFont
import com.example.facetimeclonecompose.presentation.utilities.Screen
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IncomingCallScreen(
    navController: NavController,
    roomId: String, authorName: String, roomType: String,
    viewModel: IncomingCallViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is IncomingCallViewModel.UiEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)
                IncomingCallViewModel.UiEvent.JoinedSuccessfully -> {
                    navController.navigate(Screen.HomeScreen.route){
                        popUpTo(0)
                    }
                }
            }
        }
    }
    Scaffold(
        containerColor = DarkGray,
        contentColor = DarkGray,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .padding(top = 65.sdp, bottom = 30.sdp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = authorName,
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Bold,
                color = DarkWhite,
                fontSize = 30.ssp
            )
            Text(
                modifier = Modifier.padding(top = 5.sdp),
                text = roomType,
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Medium,
                color = Gray30,
                fontSize = 17.ssp
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.sdp),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(modifier = Modifier
                        .clip(CircleShape)
                        .background(RedLight)
                        .size(60.sdp), onClick = {
                            navController.popBackStack()
                        }) {
                        Icon(
                            modifier = Modifier.size(25.sdp),
                            imageVector = Icons.Rounded.CallEnd,
                            contentDescription = "drawable icons",
                            tint = Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.size(3.sdp))
                    Text(
                        text = "Decline",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        fontSize = 10.ssp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(modifier = Modifier
                            .clip(CircleShape)
                            .background(GreenLightSecond)
                            .size(60.sdp), onClick = {
                                viewModel.joinRoom(roomId)
                            }) {
                            Icon(
                                modifier = Modifier.size(25.sdp),
                                imageVector = Icons.Rounded.Call,
                                contentDescription = "drawable icons",
                                tint = Color.White,
                            )
                        }
                        Spacer(modifier = Modifier.size(3.sdp))
                        Text(
                            text = "Agree",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                            fontSize = 10.ssp
                        )
                    }
                }

            }
        }
    }
}