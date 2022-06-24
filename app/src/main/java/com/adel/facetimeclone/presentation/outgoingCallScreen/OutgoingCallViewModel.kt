package com.adel.facetimeclone.presentation.outgoingCallScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.usecases.KickOutAllRoomUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OutgoingCallViewModel @Inject constructor(
    val kickOutAllRoomUsersUseCase: KickOutAllRoomUsersUseCase
) : ViewModel() {
    var isRoomClosedSuccess:MutableLiveData<Result<Any>> = MutableLiveData(Result.Loading())
    fun closeRoom(roomKey: String,users:List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val kickOutAllRoomUsersResult = kickOutAllRoomUsersUseCase.invoke(roomKey,users)
            isRoomClosedSuccess.postValue(kickOutAllRoomUsersResult)
        }
    }

}