package com.example.facetimeclonecompose.data.repositories

import com.example.facetimeclonecompose.data.sources.local.dataSources.UserLocalDataSource
import com.example.facetimeclonecompose.data.sources.remote.dataSources.UserRemoteDataSource
import com.example.facetimeclonecompose.data.sources.remote.requestModels.EditFcmTokenRequestModel
import com.example.facetimeclonecompose.data.sources.remote.requestModels.LoginRequestModel
import com.example.facetimeclonecompose.data.sources.remote.requestModels.RegisterRequestModel
import com.example.facetimeclonecompose.data.utilities.makeRequestAndHandleErrors
import com.example.facetimeclonecompose.domain.models.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val externalScope: CoroutineScope
) {
    suspend fun login(email: String, password: String): UserModel? = makeRequestAndHandleErrors {
        userRemoteDataSource.loginWithEmailAndPassword(LoginRequestModel(email, password))
    }?.let {
        it.userToken?.let { userToken ->
            userLocalDataSource.saveUserToken(userToken)
        } ?: throw Exception("UserToken not found")
        it.toUserModel()
    }

    suspend fun createNewAccount(name: String, email: String, password: String): UserModel? =
        makeRequestAndHandleErrors {
            userRemoteDataSource.createNewAccount(
                RegisterRequestModel(
                    email = email, name = name, password
                )
            )
        }?.let {
            it.userToken?.let { userToken ->
                userLocalDataSource.saveUserToken(userToken)
            } ?: throw Exception("UserToken not found")
            it.toUserModel()
        }

    suspend fun getUserProfileData(): UserModel = makeRequestAndHandleErrors {
        userRemoteDataSource.getProfileData()
    }?.toUserModel() ?: throw Exception("User not found")

    suspend fun sendVerificationEmail() {
        externalScope.launch {
            makeRequestAndHandleErrors {
                userRemoteDataSource.sendVerificationCode()
            }
        }.join()
    }

    suspend fun verifyOtpCode(otpCode: Int):UserModel {
        return makeRequestAndHandleErrors {
            userRemoteDataSource.verifyOtpCode(otpCode = otpCode)
        }.let {
            it?.userToken?.let { userToken ->
                userLocalDataSource.saveUserToken(userToken)
            } ?: throw Exception("UserToken not found")
            it.toUserModel()
        }
    }

    suspend fun deleteUserAccount() {
        externalScope.launch {
            makeRequestAndHandleErrors {
                userRemoteDataSource.deleteAccount()
            }.also {
                userLocalDataSource.deleteUserToken()
            }
        }.join()
    }
    suspend fun updateUserFcmToken(fcmToken:String){
        makeRequestAndHandleErrors {
            //TODO(EditFcmTokenRequestModel TO Single String Arg)
            userRemoteDataSource.editUserFcmToken(EditFcmTokenRequestModel(fcmToken))
        }
    }
}