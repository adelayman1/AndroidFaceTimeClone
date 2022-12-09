package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.repository.RoomRepository
import java.util.Date
import javax.inject.Inject

class GenerateRoomLinkUseCase @Inject constructor(
    var roomRepository: RoomRepository,
    var userRepository: UserRepositoryImpl
) {
    suspend operator fun invoke(): String {
        val userEmail = userRepository.getUser()?.email ?: throw Exception("error email not found")
        val roomModel =
            RoomModel("link", userEmail.replace(".", ","), HashMap(), Date().time.toString())
        return roomRepository.createRoom(roomModel) ?: throw Exception("cannot create room")
    }
}