package com.example.facetimeclonecompose.presentation.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.usecases.CreateLinkRoomUseCase
import com.example.facetimeclonecompose.domain.usecases.GetUserRoomsUseCase
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.HomeUiEvent
import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.RoomsUiState
import com.example.facetimeclonecompose.presentation.mappers.RoomToUiStateMapper
import com.example.facetimeclonecompose.presentation.utilities.Constants.CALL_LINK_BASE_URL
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
    private val roomToUiStateMapper: RoomToUiStateMapper
) : ViewModel() {
    var roomsUiState by mutableStateOf(RoomsUiState(isLoading = true))
        private set

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private var allRooms: MutableList<RoomModel> = mutableListOf()

    init {
        loadRooms()
    }

    private fun loadRooms() = viewModelScope.launch {
        roomsUiState = roomsUiState.copy(isLoading = true)
        try {
            allRooms = getUserRoomsUseCase()?.toMutableList() ?: mutableListOf()
            updateRoomsUiState()
        } catch (e: Exception) {
            handleGeneralError(e)
        } finally {
            roomsUiState = roomsUiState.copy(isLoading = false)
        }
    }

    // TODO("REFACTOR TO CLEAN CODE AND SORT FUNCTIONS CORRECTLY")
    private fun createLinkRoom() = viewModelScope.launch {
        roomsUiState = roomsUiState.copy(isLoading = true)
        try {
            createLinkRoomUseCase().apply {
                addNewItemToRoomsUiState(this)
                _eventFlow.emit(UiEvent.ShowShareLinkSheet(CALL_LINK_BASE_URL + roomId))
            }
        } catch (e: Exception) {
            handleGeneralError(e)
        } finally {
            roomsUiState = roomsUiState.copy(isLoading = false)
        }
    }

    private fun updateRoomsUiState() {
        roomsUiState = roomsUiState.copy(
            rooms = allRooms.map { roomToUiStateMapper.map(it, allRooms) },
            noRooms = allRooms.isEmpty()
        )
    }

    private fun addNewItemToRoomsUiState(newRoom: RoomModel) {
        allRooms.add(0, newRoom)
        updateRoomsUiState()
    }

//   YOU CAN ALSO USE THIS FUNCTION TO ADD NEW ITEM TO ROOMS UI STATE
//   WITHOUT UPDATING THE WHOLE LIST , THIS WILL BE MORE EFFICIENT IN BIGGER LISTS.
//   I DIDN'T USE IT BECAUSE I DIDN'T WANT TO MAKE THE CODE MORE COMPLEX
//
//    private fun addNewItemToRoomsUiState(newRoom: RoomModel) {
//        allRooms.add(0, newRoom)
//        val tempRoomsList = listOf(roomToUiStateMapper.map(newRoom, allRooms)) + roomsUiState.rooms
//        val updatedRoomsList = updatePreviousItem(tempRoomsList)
//        roomsUiState = roomsUiState.copy(
//            rooms = updatedRoomsList, noRooms = false
//        )
//    }
//
//    private fun updatePreviousItem(roomsList: List<RoomItemUiState>): List<RoomItemUiState> {
//        if (roomsList.size > 1) {
//            val prevItemIndex = 1
//            val prevRoom = roomToUiStateMapper.map(allRooms[prevItemIndex], allRooms)
//            return roomsList.copy { this[prevItemIndex] = prevRoom }
//        }
//        return roomsList
//    }

    private suspend fun handleGeneralError(e: Exception) {
        e.printStackTrace()
        _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
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