package com.example.facetimeclonecompose.presentation.createRoomScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.facetimeclonecompose.presentation.createRoomScreen.components.TextFieldContent
import com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates.NewRoomUiEvent
import com.example.facetimeclonecompose.presentation.homeScreen.HomeViewModel
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGraySecond
import com.example.facetimeclonecompose.presentation.ui.theme.DisabledColor
import com.example.facetimeclonecompose.presentation.ui.theme.DisabledColorSecond
import com.example.facetimeclonecompose.presentation.ui.theme.Green
import com.example.facetimeclonecompose.presentation.ui.theme.GreenDark
import com.example.facetimeclonecompose.presentation.ui.theme.GreenLight
import com.example.facetimeclonecompose.presentation.utilities.Screen
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateRoomScreen(
    navController: NavController,
    viewModel: CreateRoomViewModel = hiltViewModel()
) {
    val (focusRequester) = FocusRequester.createRefs()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {

                is CreateRoomViewModel.UiEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)
                is CreateRoomViewModel.UiEvent.AudioCallCreatedSuccessfully -> {
                    val userInfo = JitsiMeetUserInfo()
                    userInfo.displayName = event.userName
                    val conferenceOptions: JitsiMeetConferenceOptions =
                        JitsiMeetConferenceOptions.Builder()
                            .setRoom("https://meet.jit.si/${event.roomKey}")
                            .setAudioMuted(false)
                            .setUserInfo(userInfo)
                            .setVideoMuted(true)
                            .setFeatureFlag("prejoinpage.enabled", false)
                            .build()
                    JitsiMeetActivity.launch(context, conferenceOptions)
                }
                is CreateRoomViewModel.UiEvent.VideoCallCreatedSuccessfully -> {
                    val userInfo = JitsiMeetUserInfo()
                    userInfo.displayName = event.userName
                    val conferenceOptions: JitsiMeetConferenceOptions =
                        JitsiMeetConferenceOptions.Builder()
                            .setRoom("https://meet.jit.si/${event.roomKey}")
                            .setAudioMuted(false)
                            .setUserInfo(userInfo)
                            .setVideoMuted(false)
                            .setFeatureFlag("prejoinpage.enabled", false)
                            .build()
                    JitsiMeetActivity.launch(context, conferenceOptions)
                }
            }
        }
    }
    Scaffold(
        containerColor = DarkGray,
        contentColor = DarkGray,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        if (viewModel.createRoomUiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .padding(start = 15.sdp, end = 20.sdp, top = 20.sdp, bottom = 20.sdp),
                horizontalAlignment = Alignment.Start
            ) {
                TextButton(onClick = {
                    navController.navigate(Screen.HomeScreen.route)
                }) {
                    Text(
                        text = "Cancel",
                        fontSize = 13.ssp,
                        color = Green
                    )
                }
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
                    onValueChanged = { viewModel.onEvent(NewRoomUiEvent.EmailChanged(it)) },
                    focusRequester = focusRequester,
                    onClickEnter = { viewModel.onEvent(NewRoomUiEvent.AddNewParticipant) },
                )

                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom
                ) {


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .alpha(if (false) 1.0f else 0.5f)
                            .size(32.sdp)
                            .background(
                                if (viewModel.createRoomUiState.isButtonsEnabled) GreenDark else DisabledColor,
                                CircleShape
                            )
                            .clickable(
                                enabled = viewModel.createRoomUiState.isButtonsEnabled,
                                onClick = {
                                    viewModel.onEvent(NewRoomUiEvent.CreateAudioCall)
                                }
                            )
                    ) {
                        //internal circle with icon
                        Icon(
                            imageVector = Icons.Rounded.Phone,
                            contentDescription = "contentDescription",
                            modifier = Modifier
                                .size(19.sdp)
                                .background(
                                    if (viewModel.createRoomUiState.isButtonsEnabled) GreenDark else DisabledColor,
                                    CircleShape
                                )
                                .padding(2.sdp),
                            tint = if (viewModel.createRoomUiState.isButtonsEnabled) Color.Green else DisabledColorSecond
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
                                .background(
                                    if (viewModel.createRoomUiState.isButtonsEnabled) GreenLight else DisabledColor,
                                    RoundedCornerShape(8.sdp)
                                )
                                .clickable(
                                    enabled = viewModel.createRoomUiState.isButtonsEnabled,
                                    onClick = {
                                        viewModel.onEvent(NewRoomUiEvent.CreateVideoCall)
                                    })
                        ) {
                            Row(
                                Modifier
                                    .padding(start = 12.sdp, top = 2.sdp, bottom = 2.sdp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Videocam,
                                    contentDescription = "contentDescription",
                                    modifier = Modifier
                                        .size(22.sdp),
                                    tint = if (viewModel.createRoomUiState.isButtonsEnabled) Color.White else DisabledColorSecond
                                )
                                Spacer(modifier = Modifier.width(5.sdp))
                                Text(
                                    text = "FaceTime",
                                    fontSize = 12.ssp,
                                    color = if (viewModel.createRoomUiState.isButtonsEnabled) Color.White else DisabledColorSecond,
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