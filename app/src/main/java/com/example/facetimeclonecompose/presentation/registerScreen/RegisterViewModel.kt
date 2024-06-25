package com.example.facetimeclonecompose.presentation.registerScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.usecases.CreateAccountUseCase
import com.example.facetimeclonecompose.domain.utilities.InvalidConfirmPasswordException
import com.example.facetimeclonecompose.domain.utilities.InvalidEmailException
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.domain.utilities.InvalidNameException
import com.example.facetimeclonecompose.domain.utilities.InvalidPasswordException
import com.example.facetimeclonecompose.presentation.registerScreen.uiStates.RegisterUiEvent
import com.example.facetimeclonecompose.presentation.registerScreen.uiStates.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: CreateAccountUseCase) :
    ViewModel() {
    var registerUiState by mutableStateOf(RegisterUiState(isLoading = false))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    // TODO("Refactoring")
    fun register() {
        viewModelScope.launch {
            registerUiState = registerUiState.copy(isLoading = true)
            try {
                registerUseCase(
                    userName = registerUiState.nameUiState.text,
                    email = registerUiState.emailUiState.text,
                    password = registerUiState.passwordUiState.text,
                    confirmPassword = registerUiState.confirmPasswordUiState.text
                ).let {
                    _eventFlow.emit(UiEvent.RegisterSuccess)
                }
            } catch (e: InvalidInputTextException) {
                handleInvalidInput(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                registerUiState = registerUiState.copy(isLoading = false)
            }
        }
    }

    private suspend fun handleGeneralError(e: Exception) {
        e.printStackTrace()
        _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
    }

    private fun handleInvalidInput(e: InvalidInputTextException) {
        when (e) {
            is InvalidEmailException -> {
                registerUiState = registerUiState.copy(
                    emailUiState = registerUiState.emailUiState.copy(
                        errorMessage = e.message
                    )
                )
            }

            is InvalidPasswordException -> {
                registerUiState = registerUiState.copy(
                    passwordUiState = registerUiState.passwordUiState.copy(
                        errorMessage = e.message
                    )
                )
            }

            is InvalidNameException -> {
                registerUiState = registerUiState.copy(
                    nameUiState = registerUiState.nameUiState.copy(
                        errorMessage = e.message
                    )
                )
            }

            is InvalidConfirmPasswordException -> {
                registerUiState = registerUiState.copy(
                    confirmPasswordUiState = registerUiState.confirmPasswordUiState.copy(
                        errorMessage = e.message
                    )
                )
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