package com.example.facetimeclonecompose.presentation.mappers

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.RoomItemUiState
import com.example.facetimeclonecompose.presentation.utilities.DateAndTimeUtils
import com.example.facetimeclonecompose.presentation.utilities.RoomItemPositionUtil
import javax.inject.Inject

class RoomToUiStateMapper @Inject constructor(
    private val roomItemPositionUtil: RoomItemPositionUtil
) {
    fun map(room: RoomModel, allRooms: List<RoomModel>): RoomItemUiState {
        return RoomItemUiState(
            roomId = room.roomId,
            roomTitle = room.roomTitle ?: "Empty Room",
            time = DateAndTimeUtils.covertTimeToText(room.time),
            itemPosition = roomItemPositionUtil.getRoomItemPosition(room.roomId, allRooms),
            roomTypeId = room.roomTypeId,
            roomType = room.roomType,
            isMissedCall = room.isMissed
        )
    }
}
//TODO("EDIT UI CODE AND MAKE IT CLEAN AND READABLE")