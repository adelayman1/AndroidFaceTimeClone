package com.example.facetimeclonecompose.data.repositories

import com.example.facetimeclonecompose.data.sources.local.dataSources.UserLocalDataSource
import com.example.facetimeclonecompose.data.sources.remote.dataSources.UserRemoteDataSource
import com.example.facetimeclonecompose.data.sources.remote.requestModels.EditFcmTokenRequestModel
import com.example.facetimeclonecompose.data.sources.remote.requestModels.LoginRequestModel
import com.example.facetimeclonecompose.data.sources.remote.requestModels.RegisterRequestModel
import com.example.facetimeclonecompose.data.utilities.Constants.GUEST_USER
import com.example.facetimeclonecompose.data.utilities.makeRequestAndHandleErrors
import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val externalScope: CoroutineScope
) : UserRepository {
    override suspend fun login(email: String, password: String): UserModel? =
        makeRequestAndHandleErrors {
            userRemoteDataSource.loginWithEmailAndPassword(LoginRequestModel(email, password))
        }?.let {
            it.userToken?.let { userToken ->
                userLocalDataSource.saveUserToken(userToken)
            } ?: throw Exception("UserToken not found")
            userLocalDataSource.editUserVerifyState(it.isVerified)
            it.toUserModel()
        }

    override suspend fun createNewAccount(
        name: String,
        email: String,
        password: String
    ): UserModel? =
        makeRequestAndHandleErrors {
            userRemoteDataSource.createNewAccount(
                RegisterRequestModel(
                    email = email, name = name, password
                )
            )
        }?.let {
            it.userToken?.let { userToken ->
                userLocalDataSource.saveUserToken(userToken)
                userLocalDataSource.editUserVerifyState(false)
            } ?: throw Exception("UserToken not found")
            it.toUserModel()
        }

    override suspend fun getUserProfileData(): UserModel = makeRequestAndHandleErrors {
        userRemoteDataSource.getProfileData()
    }?.toUserModel() ?: throw Exception("User not found")

    override suspend fun sendVerificationEmail() {
        externalScope.launch {
            makeRequestAndHandleErrors {
                userRemoteDataSource.sendVerificationCode()
            }
        }.join()
    }

    override suspend fun verifyOtpCode(otpCode: Int): UserModel {
        return makeRequestAndHandleErrors {
            userRemoteDataSource.verifyOtpCode(otpCode = otpCode)
        }.let {
            it?.userToken?.let { userToken ->
                userLocalDataSource.saveUserToken(userToken)
                userLocalDataSource.editUserVerifyState(true)
            } ?: throw Exception("UserToken not found")
            it.toUserModel()
        }
    }

    override suspend fun deleteUserAccount() {
        externalScope.launch {
            makeRequestAndHandleErrors {
                userRemoteDataSource.deleteAccount()
            }.also {
                userLocalDataSource.deleteUserToken()
            }
        }.join()
    }

    override suspend fun updateUserFcmToken(fcmToken: String) {
        makeRequestAndHandleErrors {
            //TODO(EditFcmTokenRequestModel TO Single String Arg)
            userRemoteDataSource.editUserFcmToken(EditFcmTokenRequestModel(fcmToken))
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return userLocalDataSource.getUserToken() != GUEST_USER
    }

    override suspend fun isUserAccountVerified(): Boolean {
        return userLocalDataSource.getUserVerifiedState()
    }
}