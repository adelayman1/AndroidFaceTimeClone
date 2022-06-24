package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.model.ParticipantModel
import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.data.repository.RoomRepositoryImpl
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import java.util.*
import javax.inject.Inject


class CreateRoomCallUseCase @Inject constructor(
    var roomRepository: RoomRepositoryImpl,
    var userRepository: UserRepositoryImpl,
    var createFcmRequestUseCase: CreateFcmRequestUseCase
) {
    suspend operator fun invoke(
        toUsersList: List<String>,
        roomType: String
    ): Result<Pair<String, List<String>>> {
        val toUsersMap: Map<String, ParticipantModel> =
            toUsersList.associate { it.replace(".",",") to ParticipantModel(it, true) }
       val userEmail =  userRepository.getUser()!!.email!!
        return try {
            val roomModel = RoomModel(roomType,userEmail.replace(".",","), toUsersMap, Date().time.toString())
            val createRoomResult = roomRepository.createRoom(roomModel)
            return when (createRoomResult) {
                is Result.Success -> {
                    val getUserNameResult = userRepository.getUserName(userEmail)
                    when (getUserNameResult) {
                        is Result.Success -> {
                            var createFcmRequestResult = createFcmRequestUseCase.invoke(
                                    getUserNameResult.data,
                                    createRoomResult.data,
                                    toUsersList,
                                    roomType
                            )
                            when(createFcmRequestResult){
                                is Result.Success -> Result.Success(Pair(createRoomResult.data,toUsersList))
                                is Result.Error -> Result.Error(createFcmRequestResult.error,createFcmRequestResult.msg)
                                else -> TODO()
                            }
                        }
                        is Result.Error-> Result.Error(getUserNameResult.error,getUserNameResult.msg)
                        else -> TODO()
                    }
                }
                is Result.Error-> Result.Error(createRoomResult.error,createRoomResult.msg)
                else -> TODO()
            }
        }catch (e:Exception){
            Result.Error(ErrorEntity.WriteDataError,e.message.toString())
        }
    }
}