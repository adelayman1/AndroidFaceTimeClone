package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.RoomRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import javax.inject.Inject

class KickOutAllRoomUsersUseCase @Inject constructor(val roomRepository: RoomRepositoryImpl) {
    suspend operator fun invoke(roomKey:String,users:List<String>):Result<Any>{
        val sendFcmResult = roomRepository.sendFcmData("response","cancel","none",roomKey,"none",users)
        return if(sendFcmResult.isSuccessful)
            Result.Success("a")
        else
            Result.Error(ErrorEntity.WriteDataError,sendFcmResult.message().toString())
    }
}
