package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val userRepository: UserRepository,private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase) {
    suspend operator fun invoke(userEmail:String?=null): UserModel {
        checkIsAccountValidUseCase()
        return if(userEmail==null)
            userRepository.getUserProfileDataById() //My Profile
        else
            userRepository.getUserProfileDataByEmail(userEmail)
    }
}