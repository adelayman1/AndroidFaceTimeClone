package com.adel.facetimeclone.presentation.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.usecases.LoginUseCase
import com.adel.facetimeclone.presentation.loginScreen.uiStates.LoginUiEvent
import com.adel.facetimeclone.presentation.loginScreen.uiStates.LoginUiState
import com.adel.facetimeclone.presentation.signUpScreen.uiStates.SignupUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private var _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow: SharedFlow<LoginUiEvent> = _eventFlow.asSharedFlow()
    fun login(email: String, password: String) {
        _loginUiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginResult = loginUseCase(email, password).length
                _eventFlow.emit(LoginUiEvent.LoginSuccess)
                _loginUiState.update { currentUiState ->
                    currentUiState.copy(isLoading = false)
                }
            }catch (e:Exception){
                _eventFlow.emit(LoginUiEvent.ShowMessage(e.message.toString()))
            }
        }
    }
}