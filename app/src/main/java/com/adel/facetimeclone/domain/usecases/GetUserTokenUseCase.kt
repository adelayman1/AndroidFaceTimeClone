package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(val userRepository: UserRepositoryImpl) {
    suspend operator fun invoke(usersEmailsList: List<String>): Result<List<String>>{
            var tempUsersTokensList: MutableList<String> = mutableListOf()
            tempUsersTokensList.clear()
            for (user in usersEmailsList) {
                val getUserTokenResult=userRepository.getUserToken(user)
                when(getUserTokenResult){
                    is Result.Success ->{
                        tempUsersTokensList.add(getUserTokenResult.data)
                    }
                    is Result.Error-> return Result.Error(getUserTokenResult.error,getUserTokenResult.msg)
                    else -> TODO()
                }
            }
           return Result.Success(tempUsersTokensList)
    }
}