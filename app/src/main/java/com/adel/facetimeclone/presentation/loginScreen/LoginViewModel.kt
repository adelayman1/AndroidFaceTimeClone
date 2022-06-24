package com.adel.facetimeclone.presentation.loginScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var loginUseCase: LoginUseCase): ViewModel() {
    val isLoggingSuccess:MutableLiveData<Result<String>> = MutableLiveData(Result.Loading())
    val isLoading:MutableLiveData<Boolean> = MutableLiveData(false)
    fun login(email:String,password:String){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO){
           val loginResult = loginUseCase.invoke(email, password)
            isLoggingSuccess.postValue(loginResult)
            isLoading.postValue(false)
        }
    }
}