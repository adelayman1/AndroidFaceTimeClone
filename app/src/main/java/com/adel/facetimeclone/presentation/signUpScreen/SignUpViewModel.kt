package com.adel.facetimeclone.presentation.signUpScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.usecases.SignUpUseCase
import com.adel.facetimeclone.presentation.loginScreen.uiStates.LoginUiEvent
import com.adel.facetimeclone.presentation.signUpScreen.uiStates.SignupUiEvent
import com.adel.facetimeclone.presentation.signUpScreen.uiStates.SignupUiState
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
class SignUpViewModel @Inject constructor(var signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _signupUiState = MutableStateFlow<SignupUiState>(SignupUiState())
    val signupUiState: StateFlow<SignupUiState> = _signupUiState.asStateFlow()

    private var _eventFlow = MutableSharedFlow<SignupUiEvent>()
    val eventFlow: SharedFlow<SignupUiEvent> = _eventFlow.asSharedFlow()
    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _signupUiState.update { currentUiState ->
                    currentUiState.copy(isLoading = true)
                }
                val signupResult = signUpUseCase(email, password, name)
                _signupUiState.update { currentUiState ->
                    currentUiState.copy(isLoading = false, email = signupResult)
                }
                _eventFlow.emit(SignupUiEvent.SignupSuccess)
            } catch (e: Exception) {
                Log.d("TAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAG", "signUp: ${e.message.toString()}")
                _eventFlow.emit(SignupUiEvent.ShowMessage(e.message.toString()))
            }
        }
    }
}