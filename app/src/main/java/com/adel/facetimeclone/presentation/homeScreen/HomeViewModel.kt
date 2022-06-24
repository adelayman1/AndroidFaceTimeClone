package com.adel.facetimeclone.presentation.homeScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.usecases.CreateRoomLinkUseCase
import com.adel.facetimeclone.domain.usecases.GetAllCallsUseCase
import com.adel.facetimeclone.domain.usecases.UpdateTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        var createRoomLinkUseCase: CreateRoomLinkUseCase,
        var updateTokenUseCase: UpdateTokenUseCase,
        var getAllCallsUseCase: GetAllCallsUseCase
) :ViewModel() {
    var roomKey: MutableLiveData<Result<String>> = MutableLiveData(Result.Loading())
    var calls: MutableLiveData<Result<List<RoomModel>>> = MutableLiveData(Result.Loading())

    init {

        viewModelScope.launch(Dispatchers.IO) {
            getAllCallsUseCase.invoke().collect {
                calls.postValue(it)
            }
            updateTokenUseCase.invoke()
        }
    }
    fun createRoomLink() {
        viewModelScope.launch(Dispatchers.IO) {
            val createRoomLinkResult = createRoomLinkUseCase.invoke()
            roomKey.postValue(createRoomLinkResult)
        }
    }
}