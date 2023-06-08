package com.example.facetimeclonecompose.presentation.homeScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.ItemPosition
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.RoomItemUiState
import com.example.facetimeclonecompose.presentation.ui.theme.Gray60
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun RoomListItem(
    roomItemUiState: RoomItemUiState
) {
    return Column(horizontalAlignment = Alignment.Start) {
        val isDateShown = isDateShown(roomItemUiState)
        if (isDateShown) {
            Text(
                modifier = Modifier.padding(top = 20.sdp, bottom = 7.sdp),
                text = roomItemUiState.time,
                fontSize = 11.ssp,
                color = Gray60
            )
        }
        RoomCard(roomItemUiState = roomItemUiState, roomItemUiState.itemPosition.roundedCornerShape)
    }
}

private fun isDateShown(roomItemUiState: RoomItemUiState): Boolean {
    return roomItemUiState.itemPosition is ItemPosition.SeparatedItem || roomItemUiState.itemPosition is ItemPosition.FirstItem
}