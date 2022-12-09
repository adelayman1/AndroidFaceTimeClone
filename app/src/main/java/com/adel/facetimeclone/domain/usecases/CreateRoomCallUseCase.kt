package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.model.ParticipantModel
import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.repository.RoomRepository
import java.util.Date
import javax.inject.Inject


class CreateRoomCallUseCase @Inject constructor(
    var roomRepository: RoomRepository,
    var userRepository: UserRepositoryImpl,
    var sendCallInvitationUseCase: SendCallInvitationUseCase
) {
    suspend operator fun invoke(
        toUsersList: List<String>,
        roomType: String
    ): Pair<String, List<String>> {
        val toUsersMap: Map<String, ParticipantModel> =
            toUsersList.associate { it.replace(".", ",") to ParticipantModel(it, true) }
        val userEmail = userRepository.getUser()!!.email!!
        val roomModel =
            RoomModel(roomType, userEmail.replace(".", ","), toUsersMap, Date().time.toString())
        val createRoomResult = roomRepository.createRoom(roomModel)
        if (createRoomResult != null) {
            val getUserNameResult = userRepository.getUserName(userEmail)
            if (getUserNameResult != null) {
                var createCallInvitationResult = sendCallInvitationUseCase(
                    getUserNameResult,
                    createRoomResult,
                    toUsersList,
                    roomType
                )
                // roomKey,toUsersList
                return Pair(createRoomResult, toUsersList)
            } else
                throw Exception("error username not found")

        } else
            throw Exception("error cannot create room")
    }
}