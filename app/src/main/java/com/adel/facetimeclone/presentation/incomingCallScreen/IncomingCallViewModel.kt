package com.adel.facetimeclone.presentation.incomingCallScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.usecases.AgreeCallUseCase
import com.adel.facetimeclone.domain.usecases.DeclineCallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomingCallViewModel @Inject constructor(
    val agreeCallUseCase: AgreeCallUseCase,
    val declineCallUseCase: DeclineCallUseCase
) : ViewModel() {
    var isAgreeSuccess: MutableLiveData<Result<Any>> = MutableLiveData(Result.Loading())
    fun agreeCall(roomKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var agreeCallResult = agreeCallUseCase.invoke(roomKey)
            isAgreeSuccess.postValue(agreeCallResult)
        }
    }

    fun declineCall(roomKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            declineCallUseCase.invoke(roomKey)
        }
    }

}