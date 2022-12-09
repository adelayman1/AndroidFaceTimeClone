package com.adel.facetimeclone.presentation.newFaceTimeScreen.uiStates

sealed class FaceTimeUiEvent {
    data class CallCreatedSuccessfully(var roomKey: String,var invitedUsers:List<String>) : FaceTimeUiEvent()
    data class ShowMessage(var message: String) : FaceTimeUiEvent()
}