package com.example.facetimeclonecompose.presentation.splashScreen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.usecases.CheckIsAccountValidUseCase
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import com.example.facetimeclonecompose.presentation.splashScreen.uiStates.SplashUiState
import com.example.facetimeclonecompose.presentation.utilities.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) : ViewModel() {
    var isLoadingState by mutableStateOf(SplashUiState(isLoading = true))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private var _deepLinkUri: Uri? = null

    init {
        viewModelScope.launch {
            try {
                checkIsAccountValidUseCase()
                if (_deepLinkUri == null) isLoadingState = isLoadingState.copy(startDestination = Screen.HomeScreen.route)
            } catch (e: UserNotFoundException) {
                isLoadingState = isLoadingState.copy(startDestination = Screen.LoginScreen.route)
            } catch (e: UserNotVerifiedException) {
                isLoadingState = isLoadingState.copy(startDestination = Screen.OtpCodeScreen.route  )
            } catch (e: Exception) {
                e.printStackTrace()
            }finally {
                isLoadingState = isLoadingState.copy(isLoading = false)
            }
        }
    }

    fun handleDeeplink(uri: Uri) {
        viewModelScope.launch {
            _deepLinkUri = uri
            isLoadingState = isLoadingState.copy(isLoading = false)
            _eventFlow.emit(UiEvent.IncomingCall(uri))
        }
    }

    sealed class UiEvent {
        class IncomingCall(var uri: Uri) : UiEvent()
    }
}