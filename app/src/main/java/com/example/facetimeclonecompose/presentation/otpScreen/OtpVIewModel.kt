package com.example.facetimeclonecompose.presentation.otpScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.data.sources.local.dataSources.UserLocalDataSource
import com.example.facetimeclonecompose.domain.usecases.SendVerificationEmailUseCase
import com.example.facetimeclonecompose.domain.usecases.ValidateOtpCodeUseCase
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
    private val sendVerificationEmailUseCase: SendVerificationEmailUseCase,
    private val validateOtpCodeUseCase: ValidateOtpCodeUseCase
) : ViewModel() {
    var otpUiState by mutableStateOf(OtpUiState(isLoading = true))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                sendVerificationEmailUseCase().apply {
                    otpUiState = otpUiState.copy(isLoading = false)
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                otpUiState = otpUiState.copy(isLoading = false)
            }
        }
    }

    fun verifyOTP() {
        viewModelScope.launch {
            otpUiState = otpUiState.copy(isLoading = true)
            try {
                val otpValidationResult =
                    validateOtpCodeUseCase(otpUiState.initialCode.text.toInt())
                if (otpValidationResult.error != null) {
                    otpUiState = otpUiState.copy(
                        initialCode = otpUiState.initialCode.copy(
                            errorMessage = otpValidationResult.error,
                        )
                    )
                    otpUiState = otpUiState.copy(isLoading = false)
                    return@launch
                }
                verifyOtpCodeUseCase(otpUiState.initialCode.text.toInt()).apply {
                    _eventFlow.emit(UiEvent.OtpVerifiedSuccessfully)
                }
            } catch (e: InvalidInputTextException) {
                otpUiState = otpUiState.copy(
                    initialCode = otpUiState.initialCode.copy(
                        errorMessage = e.message,
                    )
                )
                otpUiState = otpUiState.copy(isLoading = false)
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                otpUiState = otpUiState.copy(isLoading = false)
            }
        }
    }

    fun onEvent(action: OtpUiEvent) {
        when (action) {
            is OtpUiEvent.OtpCodeChanged -> {
                otpUiState = otpUiState.copy(
                    initialCode = otpUiState.initialCode.copy(
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