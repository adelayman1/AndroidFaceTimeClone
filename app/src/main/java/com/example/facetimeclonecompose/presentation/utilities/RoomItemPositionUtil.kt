package com.example.facetimeclonecompose.presentation.utilities

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.ItemPosition
import javax.inject.Inject

class RoomItemPositionUtil @Inject constructor(){
    fun getRoomItemPosition(room:RoomModel,roomsList:List<RoomModel>):ItemPosition{
        val itemPosition = roomsList.indexOfFirst { it.roomId == room.roomId } ?: throw NullPointerException()
        return roomsList[itemPosition].let { item ->
            if (roomsList.size == 1) { // only 1 item
                return@let ItemPosition.SeparatedItem
            } else if ((itemPosition == 0 && DateAndTimeUtils.covertTimeToText(item.time) == DateAndTimeUtils.covertTimeToText(
                    roomsList[(itemPosition + 1)].time
                ))
            ) {
//               لو الأولاني وتحته عنصر شبه
                return@let ItemPosition.FirstItem
            } else if ((itemPosition == 0 && DateAndTimeUtils.covertTimeToText(item.time) != DateAndTimeUtils.covertTimeToText(
                    roomsList[(itemPosition + 1)].time
                ))
            ) {
                //               لو الأولاني وتحته عنصر مش شبه
                return@let ItemPosition.SeparatedItem
            } else if ((itemPosition != (roomsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                    item.time
                ) == DateAndTimeUtils.covertTimeToText(
                    roomsList[(itemPosition + 1)].time
                ) && DateAndTimeUtils.covertTimeToText(item.time) == DateAndTimeUtils.covertTimeToText(
                    roomsList[(itemPosition - 1)].time
                ))
            ) {
                //                لو مش الأخير وتحته عنصر شبه وفوقه عنصر شبه
                return@let ItemPosition.MiddleItem
            } else if ((itemPosition == (roomsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                    item.time
                ) == DateAndTimeUtils.covertTimeToText(roomsList[itemPosition - 1].time ?: "10"))
            ) {
                //                 لو الأخير وفوقه عنصر شبه
                return@let ItemPosition.LastItem
            } else if ((itemPosition == (roomsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                    item.time
                ) != DateAndTimeUtils.covertTimeToText(roomsList[itemPosition - 1].time ?: "10"))
            ) {
                //                 لو الأخير وفوقه عنصر مش شبه
                return@let ItemPosition.SeparatedItem
            } else if ((itemPosition != (roomsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                    item.time
                ) != DateAndTimeUtils.covertTimeToText(
                    roomsList[itemPosition - 1].time ?: "10"
                ) && DateAndTimeUtils.covertTimeToText(item.time) == DateAndTimeUtils.covertTimeToText(
                    roomsList[itemPosition + 1].time ?: "10"
                ))
            ) {
                //                لو مش الأخير وفوقه عنصر مش شبه وتحته عنصر شبه
                return@let ItemPosition.FirstItem
            } else if ((itemPosition != (roomsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                    item.time
                ) == DateAndTimeUtils.covertTimeToText(
                    roomsList[itemPosition - 1].time ?: "10"
                ) && DateAndTimeUtils.covertTimeToText(item.time) != DateAndTimeUtils.covertTimeToText(
                    roomsList[itemPosition + 1].time ?: "10"
                ))
            ) {
                //                لو مش الأخير وفوقه عنصر شبه وتحته عنصر مش شبه
                return@let ItemPosition.LastItem
            } else if ((itemPosition != (roomsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                    item.time
                ) != DateAndTimeUtils.covertTimeToText(
                    roomsList[itemPosition + 1].time ?: "10"
                ) && DateAndTimeUtils.covertTimeToText(item.time) != DateAndTimeUtils.covertTimeToText(
                    roomsList[itemPosition - 1].time ?: "10"
                ))
            ) {
                //                لو مش الأخير وتحته عنصر مش شبه وفوقه عنصر مش شبه
                return@let ItemPosition.SeparatedItem
            }else{
                // unknown case
                return@let ItemPosition.SeparatedItem
            }
        }
    }

}