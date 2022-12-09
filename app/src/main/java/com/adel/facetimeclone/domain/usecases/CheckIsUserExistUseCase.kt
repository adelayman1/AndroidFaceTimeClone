package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import javax.inject.Inject

class CheckIsUserExistUseCase @Inject constructor(var userRepository: UserRepositoryImpl){
    suspend operator fun invoke(userEmail:String): Pair<String,Boolean>{
        val isUserInDataBaseResult = userRepository.isUserExist(userEmail)
        if(!isUserInDataBaseResult){
            throw Exception("user is not exist")
        }
        //return email and isExist
        return Pair(userEmail,isUserInDataBaseResult)
    }
}