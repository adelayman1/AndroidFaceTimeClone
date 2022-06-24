package com.adel.facetimeclone.presentation.newFaceTimeScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.usecases.CheckIsUserInDataBaseUseCase
import com.adel.facetimeclone.domain.usecases.CreateRoomCallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFaceTimeViewModel @Inject constructor(var checkIsUserInDataBaseUseCase: CheckIsUserInDataBaseUseCase,
                                               var createRoomCallUseCase: CreateRoomCallUseCase) : ViewModel() {
    var isUserDownloadedApp: MutableLiveData<Result<Pair<String, Boolean>>> = MutableLiveData(Result.Null())
    var isCreatedCallSuccess: MutableLiveData<Result<Pair<String, List<String>>>> = MutableLiveData(Result.Null())
    fun checkIsUserDownloadedTheApp(userEmail: String) {
        isUserDownloadedApp.value = Result.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val checkIsUserInDataBaseResult = checkIsUserInDataBaseUseCase.invoke(userEmail)
            isUserDownloadedApp.postValue(checkIsUserInDataBaseResult)
        }
    }

    fun call(to: List<String>, type: String) {
        viewModelScope.launch(Dispatchers.IO){
            var createRoomCallResult = createRoomCallUseCase.invoke(to, type)
            isCreatedCallSuccess.postValue(createRoomCallResult)
        }
    }
}