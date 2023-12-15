package com.example.facetimeclonecompose.presentation.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.RoomItemUiState
import com.example.facetimeclonecompose.presentation.ui.theme.Gray30
import com.example.facetimeclonecompose.presentation.ui.theme.Gray60
import com.example.facetimeclonecompose.presentation.ui.theme.Green
import com.example.facetimeclonecompose.presentation.ui.theme.LightGray
import com.example.facetimeclonecompose.presentation.utilities.Constants
import com.example.facetimeclonecompose.presentation.utilities.Constants.AUDIO_CALL_ID
import com.example.facetimeclonecompose.presentation.utilities.Constants.VIDEO_CALL_ID
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Composable
fun RoomCard(
    roomItemUiState: RoomItemUiState,
    roundShape: RoundedCornerShape = RoundedCornerShape(7.sdp, 7.sdp, 7.sdp, 7.sdp)
) {
    return Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(47.sdp),
        shape = roundShape,
        colors = CardDefaults.cardColors(containerColor = LightGray)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 2.sdp)
                .clickable {
                    roomItemUiState.onEditCall()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                Icons.Default.SupervisedUserCircle,
                modifier = Modifier
                    .size(40.sdp)
                    .clip(CircleShape),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(color = Color.White)
            )
            Column(modifier = Modifier.padding(start = 5.sdp, top = 8.sdp, bottom = 2.sdp)) {
                Text(
                    text = roomItemUiState.roomTitle,
                    modifier = Modifier.width(140.sdp),
                    fontSize = 12.ssp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Row(modifier = Modifier.padding(bottom = 2.sdp)) {
                    Icon(
                        imageVector = if (roomItemUiState.roomTypeId == AUDIO_CALL_ID) Icons.Rounded.Phone else if (roomItemUiState.roomTypeId == VIDEO_CALL_ID) Icons.Rounded.Videocam else Icons.Rounded.Link,
                        modifier = Modifier
                            .size(13.sdp),
                        contentDescription = null,
                        tint = Gray30,
                    )
                    Text(
                        text = roomItemUiState.roomType,
                        fontSize = 11.ssp,
                        modifier = Modifier.padding(start = 2.sdp),
                        color = Gray30
                    )
                }

            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = roomItemUiState.time,
                fontSize = 11.ssp,
                modifier = Modifier.padding(start = 2.sdp),
                color = Gray30
            )

            Icon(
                imageVector = Icons.Outlined.Info,
                modifier = Modifier
                    .padding(start = 4.sdp, end = 10.sdp)
                    .size(20.sdp),
                contentDescription = null,
                tint = Green,

                )
        }
    }
}