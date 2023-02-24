package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import javax.inject.Inject

class DeleteUserAccountUseCase @Inject constructor(private val userRepository: UserRepository){
    suspend operator fun invoke(){
        if(!userRepository.isUserLoggedIn())
            throw UserNotFoundException()
        userRepository.deleteUserAccount()
    }
}