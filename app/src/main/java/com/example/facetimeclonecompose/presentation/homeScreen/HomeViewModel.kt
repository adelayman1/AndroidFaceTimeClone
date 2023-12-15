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

    init {
        viewModelScope.launch {
            try {
                roomsUiState = roomsUiState.copy(isLoading = true)
                val allRooms =
                    listOf<RoomModel>(RoomModel("q", 1, "qq", "qqq", "qq", null, "1688352007025"),RoomModel("q2", 1, "qq", "qqq", "qq", null, "1688352007025"))
                if (allRooms != null) {
                    val newRoomsList = allRooms.map { room ->
                        RoomItemUiState(
                            roomId = room.roomId,
                            roomTitle = room.roomTitle ?: "Empty Room",
                            time = DateAndTimeUtils.covertTimeToText(room.time)
                                ?: "HINM", // TODO(CHange HINM)
                            itemPosition = roomItemPositionUtil.getRoomItemPosition(room, allRooms),
                            roomTypeId = room.roomTypeId,
                            roomType = room.roomType,
                            onEditCall = {
                                roomsUiState = roomsUiState.copy(rooms = roomsUiState.rooms.map {
                                    roomsUiState.rooms.find { it.roomId == room.roomId }!!.copy(time ="1666216800000")
                                })
                            }
                        )
                    }
                    println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyy yyss${newRoomsList.get(0)}")
                    roomsUiState = roomsUiState.copy(
                        rooms = newRoomsList,
                        isLoading = false
                    )
                } else {
                    //TODO("DELETE PRINTS")
                    println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyy nn")
                    roomsUiState = roomsUiState.copy(isLoading = false, noRooms = true)
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

    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
        data class ShowShareLinkSheet(var link: String) : UiEvent()
    }
}