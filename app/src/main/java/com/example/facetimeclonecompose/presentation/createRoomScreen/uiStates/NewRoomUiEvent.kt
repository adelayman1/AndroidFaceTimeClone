package com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates
sealed class NewRoomUiEvent {
    data class EmailChanged(var text:String):NewRoomUiEvent()
    object CreateAudioCall:NewRoomUiEvent()
    object CreateVideoCall:NewRoomUiEvent()
    object AddNewParticipant:NewRoomUiEvent()
}