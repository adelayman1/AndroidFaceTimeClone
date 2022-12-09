package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import javax.inject.Inject

class CheckIsLoggedInUseCase @Inject constructor(val userRepository: UserRepositoryImpl){
    operator fun invoke():Boolean= userRepository.getUser()!=null
}
