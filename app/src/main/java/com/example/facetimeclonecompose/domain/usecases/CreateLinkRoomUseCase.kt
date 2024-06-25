package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.models.RoomTypeModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import javax.inject.Inject

class CreateLinkRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    suspend operator fun invoke(): RoomModel {
        checkIsAccountValidUseCase()
        return roomRepository.createRoom(RoomTypeModel.LINK)!!.apply { this.time ="0" }
    }
}