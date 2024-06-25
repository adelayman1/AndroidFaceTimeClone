package com.example.facetimeclonecompose.presentation.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.ItemPosition
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.RoomItemUiState
import com.example.facetimeclonecompose.presentation.ui.theme.Gray50
import com.example.facetimeclonecompose.presentation.ui.theme.Gray60
import com.example.facetimeclonecompose.presentation.ui.theme.LightGray
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun RoomListItem(
    roomItemUiState: RoomItemUiState
) {
    return Column(horizontalAlignment = Alignment.Start) {
        val isDateShown = isDateShown(roomItemUiState.itemPosition)
        val isLineShown = isLineShown(roomItemUiState.itemPosition)
        if (isDateShown) {
            Text(
                modifier = Modifier.padding(top = 20.sdp, bottom = 7.sdp),
                text = roomItemUiState.time,
                fontSize = 11.ssp,
                color = Gray60
            )
        }
        RoomCard(roomItemUiState = roomItemUiState, roomItemUiState.itemPosition.roundedCornerShape)
        if (isLineShown){
            Spacer(
                modifier = Modifier
                    .height(1.sdp/2)
                    .fillMaxWidth()
                    .background(LightGray)
                    .padding(start = 14.sdp, end = 14.sdp)
                    .background(Gray50)
            )
        }
    }
}

private fun isDateShown(itemPosition: ItemPosition): Boolean {
    return itemPosition is ItemPosition.SeparatedItem || itemPosition is ItemPosition.FirstItem
}
fun isLineShown(itemPosition: ItemPosition): Boolean {
    return itemPosition is ItemPosition.MiddleItem || itemPosition is ItemPosition.FirstItem
}
