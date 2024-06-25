package com.example.facetimeclonecompose.presentation.otpScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.usecases.SendVerificationEmailUseCase
import com.example.facetimeclonecompose.domain.usecases.VerifyOtpCodeUseCase
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.presentation.otpScreen.uiStates.OtpUiEvent
import com.example.facetimeclonecompose.presentation.otpScreen.uiStates.OtpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVIewModel @Inject constructor(
    private val verifyOtpCodeUseCase: VerifyOtpCodeUseCase,
    private val sendVerificationEmailUseCase: SendVerificationEmailUseCase
) : ViewModel() {
    var otpUiState by mutableStateOf(OtpUiState(isLoading = true))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                sendVerificationEmailUseCase()
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                otpUiState = otpUiState.copy(isLoading = false)
            }
        }
    }

    private fun verifyOTP() {
        viewModelScope.launch {
            otpUiState = otpUiState.copy(isLoading = true)
            try {
                verifyOtpCodeUseCase(otpUiState.code.text.toInt()).apply {
                    _eventFlow.emit(UiEvent.OtpVerifiedSuccessfully)
                }
            } catch (e: InvalidInputTextException) {
                handleInvalidInput(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                otpUiState = otpUiState.copy(isLoading = false)
            }
        }
    }

    private fun handleInvalidInput(e: InvalidInputTextException) {
        otpUiState = otpUiState.copy(
            code = otpUiState.code.copy(
                errorMessage = e.message,
            )
        )
    }

    private suspend fun handleGeneralError(e: Exception) {
        e.printStackTrace()
        _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
    }

    fun onEvent(action: OtpUiEvent) {
        when (action) {
            is OtpUiEvent.OtpCodeChanged -> {
                otpUiState = otpUiState.copy(
                    code = otpUiState.code.copy(
                        errorMessage = null,
                        text = action.code
                    )
                )
            }

            OtpUiEvent.VerifyOTP -> {
                verifyOTP()
            }
        }
    }

    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
        object OtpVerifiedSuccessfully : UiEvent()
    }
}