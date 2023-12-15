package com.example.facetimeclonecompose.presentation.registerScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.usecases.CreateAccountUseCase
import com.example.facetimeclonecompose.domain.usecases.ValidateConfirmPasswordUseCase
import com.example.facetimeclonecompose.domain.usecases.ValidateEmailUseCase
import com.example.facetimeclonecompose.domain.usecases.ValidatePasswordUseCase
import com.example.facetimeclonecompose.domain.usecases.ValidateUserNameUseCase
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.presentation.loginScreen.LoginViewModel
import com.example.facetimeclonecompose.presentation.registerScreen.uiStates.RegisterUiEvent
import com.example.facetimeclonecompose.presentation.registerScreen.uiStates.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: CreateAccountUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase
) : ViewModel() {
    var registerUiState by mutableStateOf(RegisterUiState(isLoading = false))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    // TODO("Refactoring")
    fun register() {
        viewModelScope.launch {
            try {
                val registerResult = registerUseCase(
                    userName = registerUiState.nameUiState.text,
                    email = registerUiState.emailUiState.text,
                    password = registerUiState.passwordUiState.text,
                    confirmPassword = registerUiState.confirmPasswordUiState.text
                )

                if (registerResult.userId.isNotEmpty())
                    _eventFlow.emit(UiEvent.RegisterSuccess)
                    else
                    _eventFlow.emit(UiEvent.ShowMessage("Unknown Error"))
                registerUiState = registerUiState.copy(isLoading = false)
                Log.d("ddddddddddddddddd", "login: $registerResult")
            } catch (e: InvalidInputTextException) {
                registerUiState = registerUiState.copy(
                    emailUiState = registerUiState.emailUiState.copy(
                        errorMessage = validateEmailUseCase(registerUiState.emailUiState.text).error
                    ),
                    passwordUiState = registerUiState.passwordUiState.copy(
                        errorMessage = validatePasswordUseCase(registerUiState.passwordUiState.text).error
                    ),
                    nameUiState = registerUiState.nameUiState.copy(
                        errorMessage = validateUserNameUseCase(registerUiState.nameUiState.text).error
                    ),
                    confirmPasswordUiState = registerUiState.confirmPasswordUiState.copy(
                        errorMessage = validateConfirmPasswordUseCase(registerUiState.passwordUiState.text,registerUiState.confirmPasswordUiState.text).error
                    ),
                )
                registerUiState = registerUiState.copy(isLoading = false)
            } catch (e: Exception) {
                Log.d("ddddddddddddddddd", "login: $e")
                e.printStackTrace()
                registerUiState = registerUiState.copy(isLoading = false)
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
            }
        }
    }

    fun onEvent(action: RegisterUiEvent) {
        registerUiState = when (action) {
            is RegisterUiEvent.NameChanged -> registerUiState.copy(
                nameUiState = registerUiState.nameUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )

            is RegisterUiEvent.EmailChanged -> registerUiState.copy(
                emailUiState = registerUiState.nameUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )

            is RegisterUiEvent.PasswordChanged -> registerUiState.copy(
                passwordUiState = registerUiState.passwordUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )

            is RegisterUiEvent.ConfirmPasswordChanged -> registerUiState.copy(
                confirmPasswordUiState = registerUiState.confirmPasswordUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )
        }
    }

    sealed class UiEvent {
        object RegisterSuccess : UiEvent()
        data class ShowMessage(var error: String) : UiEvent()
    }
}