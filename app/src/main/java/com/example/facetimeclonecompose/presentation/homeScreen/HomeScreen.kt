package com.example.facetimeclonecompose.presentation.homeScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import com.example.facetimeclonecompose.presentation.ui.theme.Green
import com.example.facetimeclonecompose.presentation.homeScreen.components.DefaultCard
import com.example.facetimeclonecompose.presentation.homeScreen.components.RoomListItem
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.HomeUiEvent
import com.example.facetimeclonecompose.presentation.utilities.DateAndTimeUtils
import com.example.facetimeclonecompose.presentation.ui.theme.Gray60
import com.example.facetimeclonecompose.presentation.utilities.Screen
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeViewModel.UiEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)

                is HomeViewModel.UiEvent.ShowShareLinkSheet -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, event.link)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .background(DarkGray)
            .padding(vertical = 20.sdp, horizontal = 15.sdp),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        if(viewModel.roomsUiState.isLoading){
            CircularProgressIndicator()
        }else{
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGray)
            ) {
                Text(
                    text = "Edit",
                    fontSize = 13.ssp,
                    color = Green
                )
                Spacer(Modifier.size(5.sdp))
                Text(
                    text = "FaceTime",
                    fontSize = 22.ssp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.size(17.sdp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    DefaultCard(
                        text = "Create Link",
                        icon = Icons.Rounded.Link,
                        modifier = Modifier.weight(1f),
                        rotate = 33f,
                        onClick = {
                            viewModel.onEvent(HomeUiEvent.CreateLink)
                        })
                    Spacer(modifier = Modifier.size(10.sdp))
                    DefaultCard(
                        text = "New FaceTime",
                        icon = Icons.Rounded.Videocam,
                        modifier = Modifier.weight(1f),
                        color = Green,
                        onClick = {
                           navController.navigate(Screen.CreateRoomScreen.route)
                        })
                }
                LazyColumn(modifier = Modifier.padding(top = 20.sdp), content = {
                    if (viewModel.roomsUiState.noRooms) {
                        item() {
                            Text(
                                text = "No Rooms",
                                modifier = Modifier.padding(top = 12.sdp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.ssp,
                                color = Color.White,
                            )
                        }
                    } else {
                        items(viewModel.roomsUiState.rooms) {
                            RoomListItem(roomItemUiState = it)
                        }
                    }
                })
            }
        }

    }
}