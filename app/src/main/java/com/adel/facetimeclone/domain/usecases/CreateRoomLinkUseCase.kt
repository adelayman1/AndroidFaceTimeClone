package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.data.repository.RoomRepositoryImpl
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class CreateRoomLinkUseCase @Inject constructor(var roomRepository: RoomRepositoryImpl,var userRepository: UserRepositoryImpl) {
    suspend operator fun invoke(): Result<String> {
        return try {
            val userEmail = userRepository.getUser()!!.email!!
            val roomModel: RoomModel = RoomModel("link",userEmail.replace(".",","),HashMap(), Date().time.toString())
            roomRepository.createRoom(roomModel)
        }catch (e:Exception){
            Result.Error(ErrorEntity.GetDataError,e.message.toString())
        }
    }
}