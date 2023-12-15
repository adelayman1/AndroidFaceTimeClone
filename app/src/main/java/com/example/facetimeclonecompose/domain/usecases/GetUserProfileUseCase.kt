package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(userEmail:String?=null): UserModel {
//        if (!userRepository.isUserLoggedIn())
//            throw UserNotFoundException()
//        if (!userRepository.isUserAccountVerified())
//            throw UserNotVerifiedException()
        //TODO("DELETE")
        if(userEmail==null)
            return userRepository.getUserProfileData()//My Profile
        else
            return userRepository.getUserProfileDataByEmail(userEmail)
    }
}