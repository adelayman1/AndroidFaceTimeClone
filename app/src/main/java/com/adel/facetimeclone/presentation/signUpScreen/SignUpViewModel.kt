package com.adel.facetimeclone.presentation.signUpScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(var signUpUseCase: SignUpUseCase): ViewModel() {
    val isSignedUpSuccess: MutableLiveData<Result<String>> = MutableLiveData(Result.Loading())
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun signUp(email:String,password:String,name:String){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO){
            val loginResult = signUpUseCase.invoke(email, password,name)
            isSignedUpSuccess.postValue(loginResult)
            isLoading.postValue(false)
        }
    }
}