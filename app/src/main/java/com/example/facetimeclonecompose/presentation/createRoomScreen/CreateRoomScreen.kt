package com.example.facetimeclonecompose.presentation.createRoomScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.facetimeclonecompose.presentation.createRoomScreen.components.TextFieldContent
import com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates.NewRoomUiEvent
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGraySecond
import com.example.facetimeclonecompose.presentation.ui.theme.Green
import com.example.facetimeclonecompose.presentation.ui.theme.GreenDark
import com.example.facetimeclonecompose.presentation.ui.theme.GreenLight
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateRoomScreen(
    navController: NavController,
    viewModel: CreateRoomViewModel = hiltViewModel()
) {
    val (focusRequester) = FocusRequester.createRefs()
    Scaffold(
        containerColor = DarkGraySecond,
        contentColor = DarkGraySecond
    ) {
        if (viewModel.createRoomUiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .padding(start = 15.sdp, end = 20.sdp, top = 20.sdp, bottom = 20.sdp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 13.ssp,
                    color = Green
                )
                Spacer(modifier = Modifier.size(10.sdp))
                Text(
                    text = "New FaceTime",
                    fontSize = 23.ssp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.size(20.sdp))
                //TODO(""")
                TextFieldContent(
                    state = viewModel.createRoomUiState.participantsUiState,
                    onValueChanged = { viewModel.onEvent(NewRoomUiEvent.EmailChanged(it.text)) },
                    focusRequester = focusRequester,
                    onClickEnter = { viewModel.onEvent(NewRoomUiEvent.AddNewParticipant) },
                    onChipClick = {}
                )

                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom
                ) {


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(32.sdp)
                            .background(GreenDark, CircleShape)
                    ) {
                        //internal circle with icon
                        Icon(
                            imageVector = Icons.Rounded.Phone,
                            contentDescription = "contentDescription",
                            modifier = Modifier
                                .size(19.sdp)
                                .background(GreenDark, CircleShape)
                                .padding(2.sdp),
                            tint = Color.Green
                        )
                    }
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(32.sdp)
                                .background(GreenLight, RoundedCornerShape(8.sdp))
                        ) {
                            Row(
                                Modifier.padding(start = 12.sdp, top = 2.sdp, bottom = 2.sdp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Videocam,
                                    contentDescription = "contentDescription",
                                    modifier = Modifier
                                        .size(22.sdp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(5.sdp))
                                Text(
                                    text = "FaceTime",
                                    fontSize = 12.ssp,
                                    color = Color.White,
                                    fontFamily = FontFamily.SansSerif
                                )
                                Spacer(modifier = Modifier.width(12.sdp))
                            }
                        }
                    }
                }
            }
        }
    }
}