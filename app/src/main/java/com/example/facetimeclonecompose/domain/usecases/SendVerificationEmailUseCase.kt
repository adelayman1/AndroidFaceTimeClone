package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserVerifiedException
import javax.inject.Inject

class SendVerificationEmailUseCase @Inject constructor(private val userRepository: UserRepository){
    suspend operator fun invoke(){
        if(!userRepository.isUserLoggedIn())
            throw UserNotFoundException()
        if(userRepository.isUserAccountVerified())
            throw UserVerifiedException()
        return userRepository.sendVerificationEmail()
    }
}