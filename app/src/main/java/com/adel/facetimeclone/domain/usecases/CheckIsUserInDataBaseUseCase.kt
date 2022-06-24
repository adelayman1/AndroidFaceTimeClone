package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.Result
import javax.inject.Inject

class CheckIsUserInDataBaseUseCase @Inject constructor(var userRepository: UserRepositoryImpl){
    suspend operator fun invoke(userEmail:String): Result<Pair<String,Boolean>>{
        val isUserInDataBaseResult = userRepository.isUserInDatabase(userEmail)
        return Result.Success(Pair(userEmail,isUserInDataBaseResult))
    }
}
