package com.example.facetimeclonecompose.presentation.loginScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.usecases.LoginUseCase
import com.example.facetimeclonecompose.domain.usecases.ValidateEmailUseCase
import com.example.facetimeclonecompose.domain.usecases.ValidatePasswordUseCase
import com.example.facetimeclonecompose.domain.utilities.InvalidEmailException
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.domain.utilities.InvalidPasswordException
import com.example.facetimeclonecompose.presentation.loginScreen.uiStates.LoginUiEvent
import com.example.facetimeclonecompose.presentation.loginScreen.uiStates.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    var loginUiState by mutableStateOf(LoginUiState(isLoading = false))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun login() {
        viewModelScope.launch {
            loginUiState = loginUiState.copy(isLoading = true) // TODO("Refactor To Seperate Function")
            try {
                loginUseCase(loginUiState.emailUiState.text, loginUiState.passwordUiState.text).let { loginResult ->
                    if (loginResult.isVerified)
                        _eventFlow.emit(UiEvent.LoginSuccess)
                    else
                        _eventFlow.emit(UiEvent.VerifyAccount)
                }
            } catch (e: InvalidInputTextException) {
                handleInvalidInput(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            }finally {
                loginUiState = loginUiState.copy(isLoading = false)
            }
        }
    }
    private suspend fun handleGeneralError(e: Exception) {
        e.printStackTrace()
        _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
    }

    private fun handleInvalidInput(e: InvalidInputTextException) {
        when(e){
            is InvalidEmailException -> {
                loginUiState = loginUiState.copy(
                    emailUiState = loginUiState.emailUiState.copy(
                        errorMessage = e.message
                    )
                )
            }
            is InvalidPasswordException -> {
                loginUiState = loginUiState.copy(
                    passwordUiState = loginUiState.passwordUiState.copy(
                        errorMessage = e.message
                    )
                )
            }
        }
    }

    fun onEvent(action: LoginUiEvent) {
        loginUiState = when (action) {
            is LoginUiEvent.EmailChanged -> loginUiState.copy(
                emailUiState = loginUiState.emailUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )

            is LoginUiEvent.PasswordChanged -> loginUiState.copy(
                passwordUiState = loginUiState.passwordUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )
        }
    }

    sealed class UiEvent {
        object LoginSuccess : UiEvent()
        object VerifyAccount: UiEvent()
        data class ShowMessage(var error: String) : UiEvent()
    }
}