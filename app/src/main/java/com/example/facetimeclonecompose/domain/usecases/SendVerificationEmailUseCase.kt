package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import javax.inject.Inject

class SendVerificationEmailUseCase @Inject constructor(private val userRepository: UserRepository,private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase){
    suspend operator fun invoke(){
        try {
            checkIsAccountValidUseCase()
        }catch(e:UserNotVerifiedException){
            return userRepository.sendVerificationEmail()
        }
    }
}