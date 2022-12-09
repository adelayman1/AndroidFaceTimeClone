package com.adel.facetimeclone.presentation.mainActivity

import androidx.lifecycle.ViewModel
import com.adel.facetimeclone.domain.usecases.CheckIsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var checkIsLoggedInUseCase: CheckIsLoggedInUseCase):ViewModel() {
    fun isLoggedIn():Boolean
    = checkIsLoggedInUseCase.invoke()

}