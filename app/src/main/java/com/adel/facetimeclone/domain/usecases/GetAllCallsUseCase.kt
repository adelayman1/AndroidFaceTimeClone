package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.data.repository.RoomRepositoryImpl
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCallsUseCase @Inject constructor(
        var roomRepository: RoomRepositoryImpl,
        var userRepository: UserRepositoryImpl
) {
    suspend operator fun invoke(): Flow<Result<List<RoomModel>>> = flow {
        val userEmail = userRepository.getUser()!!.email!!
        val callsList: MutableList<RoomModel> = mutableListOf()
        roomRepository.getAllUserCalls(userEmail.replace(".", ",")).collect {
            when (it) {
                is Result.Success -> {
                    callsList.clear()
                    for (item in it.data) {
                        if (item.roomType!! != "link") {
                            if (item.roomAuthor == userEmail.replace(".", ",")) {
                                if (!item.to.isNullOrEmpty()) {
                                    val userNameResult = userRepository.getUserName(item.to.keys.first())
                                    when (userNameResult) {
                                        is Result.Success -> {
                                            callsList.add(
                                                    RoomModel(
                                                            item.roomType,
                                                            if (item.to.size == 1) userNameResult.data else (userNameResult.data + " and ${(item.to.size - 1)} others"),
                                                            item.to,
                                                            item.time
                                                    )
                                            )
                                        }
                                        else -> flow { emit(it) }
                                    }
                                }
                            } else {
                                val userNameResult = userRepository.getUserName(item.roomAuthor!!)
                                when (userNameResult) {
                                    is Result.Success -> {
                                        callsList.add(
                                                RoomModel(
                                                        item.roomType,
                                                        userNameResult.data,
                                                        item.to,
                                                        item.time
                                                )
                                        )
                                    }
                                    else -> flow { emit(it) }
                                }
                            }
                        } else {
                            callsList.add(item)
                        }
                    }
                    emit(Result.Success(callsList))
                }
                else -> {
                    flow { emit(it) }
                }
            }
        }
    }


}