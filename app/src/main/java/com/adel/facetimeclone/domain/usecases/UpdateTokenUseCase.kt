package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(val userRepository: UserRepositoryImpl) {
    suspend operator fun invoke() {
        val userEmail = userRepository.getUser()?.email ?: throw Exception("email is not valid")
        val getUserTokenResult = userRepository.getUserToken()
        userRepository.updateUserToken(userEmail, getUserTokenResult)
    }
}
