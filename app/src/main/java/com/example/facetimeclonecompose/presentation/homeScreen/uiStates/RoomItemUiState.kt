package com.example.facetimeclonecompose.presentation.homeScreen.uiStates

data class RoomItemUiState(
    var roomId:String,
    var roomTitle:String,
    var time:String,
    var itemPosition:ItemPosition,
    var roomTypeId:Int,
    var roomType:String
)