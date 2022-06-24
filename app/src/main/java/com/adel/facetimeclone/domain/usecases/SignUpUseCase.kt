package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val userRepository: UserRepositoryImpl, val updateTokenUseCase: UpdateTokenUseCase) {
    suspend operator fun invoke(
            email: String,
            password: String,
            name: String
    ): Result<String> {
        if (email.trim().isEmpty())
            return Result.Error(ErrorEntity.GetDataError, "null data")
        if (password.trim().isEmpty())
            return Result.Error(ErrorEntity.GetDataError, "null data")
        if (name.trim().isEmpty())
            return Result.Error(ErrorEntity.GetDataError, "null data")

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return Result.Error(ErrorEntity.GetDataError, "null data")
        if (password.trim().length < 6)
            return Result.Error(ErrorEntity.GetDataError, "null data")
        if (FirebaseAuth.getInstance().currentUser != null)
            return Result.Error(ErrorEntity.GetDataError, "null data")
        val createAccountWithEmailAndPasswordResult =
                userRepository.createAccountWithEmailAndPassword(email, password)
        return when (createAccountWithEmailAndPasswordResult) {
            is Result.Success -> {
                val updateUserDataResult = userRepository.updateUserData(email, name)
                when (updateUserDataResult) {
                    is Result.Success -> {
                        val getUserTokenResult = userRepository.getToken()
                        when (getUserTokenResult) {
                            is Result.Success -> {
                                val updateUserTokenResult = updateTokenUseCase.invoke()
                                when (updateUserTokenResult) {
                                    is Result.Success ->
                                        Result.Success(createAccountWithEmailAndPasswordResult.data.email!!)

                                    else -> Result.Error(ErrorEntity.WriteDataError, "unknown")
                                }
                            }
                            is Result.Error ->
                                Result.Error(getUserTokenResult.error, getUserTokenResult.msg)

                            else -> TODO()
                        }
                    }
                    is Result.Error ->
                        Result.Error(updateUserDataResult.error, updateUserDataResult.msg)

                    else -> TODO()
                }
            }
            is Result.Error ->
                Result.Error(createAccountWithEmailAndPasswordResult.error, createAccountWithEmailAndPasswordResult.msg)

            else -> TODO()
        }
    }
}
