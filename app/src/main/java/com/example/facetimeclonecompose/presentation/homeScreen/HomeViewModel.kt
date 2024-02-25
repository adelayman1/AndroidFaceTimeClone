package com.example.facetimeclonecompose.presentation.homeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.usecases.CreateLinkRoomUseCase
import com.example.facetimeclonecompose.domain.usecases.GetUserRoomsUseCase
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.HomeUiEvent
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.RoomItemUiState
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.RoomsUiState
import com.example.facetimeclonecompose.presentation.utilities.Constants
import com.example.facetimeclonecompose.presentation.utilities.Constants.DEEP_LINK_BASE_URL
import com.example.facetimeclonecompose.presentation.utilities.DateAndTimeUtils
import com.example.facetimeclonecompose.presentation.utilities.RoomItemPositionUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserRoomsUseCase: GetUserRoomsUseCase,
    private val createLinkRoomUseCase: CreateLinkRoomUseCase,
    private val roomItemPositionUtil: RoomItemPositionUtil
) : ViewModel() {
    var roomsUiState by mutableStateOf(RoomsUiState(isLoading = true))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private var allRooms: List<RoomModel>? = null

    init {
        viewModelScope.launch {
            try {
                roomsUiState = roomsUiState.copy(isLoading = true)
                allRooms = getUserRoomsUseCase()
                if (allRooms != null) {
                    updateRoomsUiState()
                } else {
                    roomsUiState = roomsUiState.copy(isLoading = false, noRooms = true)
                    _eventFlow.emit(UiEvent.ShowMessage(NullPointerException().message.toString()))
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                roomsUiState = roomsUiState.copy(isLoading = false)
            }
        }
    }

    private fun createLinkRoom() {
        viewModelScope.launch {
            try {
                roomsUiState = roomsUiState.copy(isLoading = true)
                createLinkRoomUseCase().apply {
                    roomsUiState = roomsUiState.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowShareLinkSheet(DEEP_LINK_BASE_URL + roomId))
                }
            } catch (e: Exception) {
                roomsUiState = roomsUiState.copy(isLoading = false)
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
            }
        }

    }

    fun onEvent(action: HomeUiEvent) {
        when (action) {
            HomeUiEvent.CreateLink -> createLinkRoom()
        }
    }

    private fun updateRoomsUiState(){
        val newRoomsList = allRooms!!.map { room ->
            room.toRoomUiState()
        }
        roomsUiState = roomsUiState.copy(
            rooms = newRoomsList,
            isLoading = false
        )
    }
    private fun RoomModel.toRoomUiState():RoomItemUiState {
       return RoomItemUiState(
            roomId = roomId,
            roomTitle = roomTitle ?: "Empty Room",
            time = DateAndTimeUtils.covertTimeToText(time)
                ?: "HINM", // TODO(CHange HINM)
            itemPosition = roomItemPositionUtil.getRoomItemPosition(this, allRooms!!),
            roomTypeId = roomTypeId,
            roomType = roomType,
            onEditCall = {
                roomsUiState = roomsUiState.copy(rooms = roomsUiState.rooms.map {
                    roomsUiState.rooms.find { it.roomId == roomId }!!.copy(time = "1666216800000")
                }) // TODO("Handle edit")
            }
        )
    }

    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
        data class ShowShareLinkSheet(var link: String) : UiEvent()
    }
}