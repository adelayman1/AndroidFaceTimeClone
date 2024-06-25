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
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
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
            userLocalDataSource.editUserVerifyState(it.isVerified)
            userLocalDataSource.saveUserId(it.userID)
            userLocalDataSource.saveUserToken(it.userToken?:throw Exception("UserToken not found"))
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
            userLocalDataSource.editUserVerifyState(false)
            userLocalDataSource.saveUserId(it.userID)
            userLocalDataSource.saveUserToken(it.userToken?:throw Exception("UserToken not found"))
            it.toUserModel()
        }

    override suspend fun getUserProfileDataByEmail(userEmail: String): UserModel =
        makeRequestAndHandleErrors {
            userRemoteDataSource.getProfileDataByEmail(userEmail)
        }?.toUserModel() ?: throw UserNotFoundException()

    override suspend fun getUserProfileDataById(userId: String): UserModel {
        return makeRequestAndHandleErrors {
            userRemoteDataSource.getProfileDataByID(userId)
        }?.toUserModel() ?: throw UserNotFoundException()
    }

    override suspend fun getUserProfileDataById(): UserModel {
        return makeRequestAndHandleErrors {
            userRemoteDataSource.getProfileDataByID(getUserID())
        }?.toUserModel() ?: throw UserNotFoundException()
    }


    override suspend fun sendVerificationEmail() {
//        externalScope.launch(NonCancellable) {
            makeRequestAndHandleErrors {
                userRemoteDataSource.sendVerificationCode()
            }
//        }
    }

    override suspend fun verifyOtpCode(otpCode: Int): UserModel {
        return makeRequestAndHandleErrors {
            userRemoteDataSource.verifyOtpCode(otpCode = otpCode)
        }.let {
            it?.userToken?.let {
                userLocalDataSource.saveUserToken(it)
                userLocalDataSource.editUserVerifyState(true)
            } ?: throw Exception("UserToken not found")
            it.toUserModel()
        }
    }

    override suspend fun deleteUserAccount() {
        externalScope.launch(NonCancellable) {
            makeRequestAndHandleErrors {
                userRemoteDataSource.deleteAccount()
            }.also {
                userLocalDataSource.deleteUserToken()
                userLocalDataSource.deleteUserId()
            }
        }.join()
    }

    override suspend fun updateUserFcmToken(fcmToken: String) {
        makeRequestAndHandleErrors {
            userLocalDataSource.saveFCMToken(fcmToken)
            userRemoteDataSource.editUserFcmToken(EditFcmTokenRequestModel(fcmToken))
        }
    }


    override suspend fun isUserLoggedIn(): Boolean {
        return userLocalDataSource.getUserID() != GUEST_USER
    }

    override suspend fun isUserAccountVerified(): Boolean {
        return userLocalDataSource.getUserVerifiedState()
    }

    override suspend fun getUserID(): String {
        if (userLocalDataSource.getUserID() == GUEST_USER) throw UserNotFoundException()
        else return userLocalDataSource.getUserID()
    }

    override suspend fun getFCMToken(): String = userLocalDataSource.getFCNToken()
}