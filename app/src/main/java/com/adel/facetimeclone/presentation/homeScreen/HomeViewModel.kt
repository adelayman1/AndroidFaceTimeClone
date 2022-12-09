package com.adel.facetimeclone.presentation.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.usecases.GenerateRoomLinkUseCase
import com.adel.facetimeclone.domain.usecases.GetAllCallsUseCase
import com.adel.facetimeclone.domain.usecases.UpdateTokenUseCase
import com.adel.facetimeclone.presentation.homeScreen.uiStates.CallItemUiState
import com.adel.facetimeclone.presentation.homeScreen.uiStates.HomeUiEvent
import com.adel.facetimeclone.presentation.homeScreen.uiStates.HomeUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    var generateRoomLinkUseCase: GenerateRoomLinkUseCase,
    var updateTokenUseCase: UpdateTokenUseCase,
    var getAllCallsUseCase: GetAllCallsUseCase
) : ViewModel() {
    private var _homeUiState = MutableStateFlow(HomeUiState())
    var homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    private var _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow: SharedFlow<HomeUiEvent> = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _homeUiState.update { currentUiState ->
                currentUiState.copy(isLoading = true)
            }
            try {

                updateTokenUseCase.invoke()
                getAllCallsUseCase.invoke().collect {
                    var newCallsList = it.map { callItem ->
                        CallItemUiState(
                            roomType = callItem.roomType,
                            roomAuthor = callItem.roomAuthor,
                            to = callItem.to,
                            time = callItem.time ?: ""
                        )
                    }
                    Log.d(
                        "TAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAG",
                        ": ${Gson().toJson(newCallsList)}"
                    )
                    _homeUiState.update { currentUiState ->
                        currentUiState.copy(isLoading = false, callsList = newCallsList)
                    }
                }
            } catch (e: Exception) {
                Log.d(
                    "TAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAG",
                    ": ${e.message}"
                )
                _eventFlow.emit(HomeUiEvent.ShowMessage(e.message.toString()))
            }
        }
    }

    fun createRoomLink() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _homeUiState.update { currentUiState ->
                    currentUiState.copy(isLoading = true)
                }
                val createRoomLinkResult = generateRoomLinkUseCase.invoke()
                _homeUiState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = false,
                        isLinkDialogVisible = true,
                        roomLink = createRoomLinkResult
                    )
                }
            } catch (e: Exception) {
                _homeUiState.update { currentUiState ->
                    currentUiState.copy(isLoading = false)
                }
                _eventFlow.emit(HomeUiEvent.ShowMessage(e.message.toString()))
            }
        }
    }

    fun hideShareDialog() {
        _homeUiState.update { currentUiState ->
            currentUiState.copy(isLinkDialogVisible = false)
        }
    }
}