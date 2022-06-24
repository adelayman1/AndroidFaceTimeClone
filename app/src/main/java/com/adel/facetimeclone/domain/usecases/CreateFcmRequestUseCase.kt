package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.RoomRepositoryImpl
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import javax.inject.Inject

class CreateFcmRequestUseCase @Inject constructor(
    val roomRepository: RoomRepositoryImpl,
    val userRepository: UserRepositoryImpl,
    val getUserTokenUseCase: GetUserTokenUseCase
) {
    suspend operator fun invoke(
        name: String,
        roomKey: String,
        toUsersList: List<String>,
        roomType: String
    ): Result<Boolean> {
        val getUsersTokenResult = getUserTokenUseCase.invoke(toUsersList)
        return when (getUsersTokenResult) {
            is Result.Success -> {
                try {
                    val userEmai = userRepository.getUser()!!.email!!
                    var sendFcmDataResult = roomRepository.sendFcmData(
                            "call",
                            roomType,
                            name,
                            roomKey,
                            userEmai,
                            getUsersTokenResult.data
                    )
                    if (sendFcmDataResult.isSuccessful)
                        Result.Success(true)
                    else
                        Result.Error(ErrorEntity.WriteDataError,"unknown error")
                }
                catch (e:Exception){
                    Result.Error(ErrorEntity.GetDataError,e.message.toString())
                }

            }
            is Result.Error -> Result.Error(getUsersTokenResult.error,getUsersTokenResult.msg)
            else -> TODO()
        }
    }
}