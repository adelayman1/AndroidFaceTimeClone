package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): UserModel {
        if (!userRepository.isUserLoggedIn())
            throw UserNotFoundException()
        if (!userRepository.isUserAccountVerified())
            throw UserNotVerifiedException()
        return userRepository.getUserProfileData()
    }
}