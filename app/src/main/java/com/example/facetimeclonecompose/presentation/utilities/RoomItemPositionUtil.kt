package com.example.facetimeclonecompose.presentation.utilities

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.ItemPosition
import javax.inject.Inject

class RoomItemPositionUtil @Inject constructor(){
    fun getRoomItemPosition(roomId:String,roomsList:List<RoomModel>):ItemPosition{
        val itemPosition = roomsList.indexOfFirst { it.roomId == roomId }
        if (itemPosition == -1) {
            throw IllegalArgumentException("Room not found in list")
        }

        val currentItemTime = DateAndTimeUtils.covertTimeToText(roomsList[itemPosition].time)
        val nextItemTime = if (itemPosition + 1 < roomsList.size) DateAndTimeUtils.covertTimeToText(roomsList[itemPosition + 1].time) else null
        val prevItemTime = if (itemPosition - 1 >= 0) DateAndTimeUtils.covertTimeToText(roomsList[itemPosition - 1].time) else null

        return when {
            roomsList.size == 1 -> ItemPosition.SeparatedItem
            itemPosition == 0 && currentItemTime == nextItemTime -> ItemPosition.FirstItem
            itemPosition == 0 -> ItemPosition.SeparatedItem
            itemPosition == roomsList.size - 1 && currentItemTime == prevItemTime -> ItemPosition.LastItem
            itemPosition == roomsList.size - 1 -> ItemPosition.SeparatedItem
            currentItemTime == nextItemTime && currentItemTime == prevItemTime -> ItemPosition.MiddleItem
            currentItemTime == nextItemTime -> ItemPosition.FirstItem
            currentItemTime == prevItemTime -> ItemPosition.LastItem
            else -> ItemPosition.SeparatedItem
        }
    }

}
//inline fun <T> List<T>.copy(block: MutableList<T>.() -> Unit): List<T> {
//    return toMutableList().apply(block)
//}