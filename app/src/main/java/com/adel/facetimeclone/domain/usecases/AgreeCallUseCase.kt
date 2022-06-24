package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.RoomRepositoryImpl
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import javax.inject.Inject

class AgreeCallUseCase @Inject constructor(var roomRepository: RoomRepositoryImpl, var userRepository: UserRepositoryImpl) {
    suspend operator fun invoke(roomKey: String): Result<Any> {
        return try {
            val userEmail = userRepository.getUser()!!.email!!
            roomRepository.changeIsUserMissedCallValue(roomKey, userEmail, false)
        }catch (e:Exception){
            Result.Error(ErrorEntity.GetDataError,e.message.toString())
        }
    }
}
