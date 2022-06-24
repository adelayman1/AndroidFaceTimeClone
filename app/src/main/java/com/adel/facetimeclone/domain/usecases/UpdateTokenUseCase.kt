package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(val userRepository: UserRepositoryImpl) {
    suspend operator fun invoke(): Result<Any> {
        return try {
            val userEmail = userRepository.getUser()!!.email!!
            val getUserTokenResult = userRepository.getToken()
            when (getUserTokenResult) {
                is Result.Success -> {
                    val updateUserTokenResult =
                            userRepository.updateUserToken(userEmail, getUserTokenResult.data)
                    when (updateUserTokenResult) {
                        is Result.Success ->
                            Result.Success(true)

                        is Result.Error ->
                            Result.Error(updateUserTokenResult.error,updateUserTokenResult.msg)

                        else -> TODO()
                    }
                }
                is Result.Error ->
                    Result.Error(getUserTokenResult.error,getUserTokenResult.msg)

                else -> TODO()
            }
        } catch (e: Exception) {
            Result.Error(ErrorEntity.GetDataError,e.message.toString())
        }
    }
}
