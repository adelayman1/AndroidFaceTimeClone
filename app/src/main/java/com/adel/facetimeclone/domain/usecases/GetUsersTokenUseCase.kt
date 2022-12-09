package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import javax.inject.Inject

class GetUsersTokenUseCase @Inject constructor(val userRepository: UserRepositoryImpl) {
    suspend operator fun invoke(usersEmailsList: List<String>): List<String>{
            var tempUsersTokensList: MutableList<String> = mutableListOf()
            tempUsersTokensList.clear()
            for (user in usersEmailsList) {
                val getUserTokenResult=userRepository.getUserToken(user)
                tempUsersTokensList.add(getUserTokenResult?:throw Exception("error token ot found"))
            }
           return tempUsersTokensList
    }
}