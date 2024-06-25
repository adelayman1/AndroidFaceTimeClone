package com.example.facetimeclonecompose.data.sources.local.dataSources

import com.example.facetimeclonecompose.data.utilities.Constants.GUEST_USER
import com.example.facetimeclonecompose.data.utilities.Constants.USER_FCM_TOKEN_PREFERENCE_DATA_STORE_KEY
import com.example.facetimeclonecompose.data.utilities.Constants.USER_ID_PREFERENCE_DATA_STORE_KEY
import com.example.facetimeclonecompose.data.utilities.Constants.USER_TOKEN_PREFERENCE_DATA_STORE_KEY
import com.example.facetimeclonecompose.data.utilities.Constants.USER_VERIFIED_PREFERENCE_DATA_STORE_KEY
import com.example.facetimeclonecompose.data.utilities.PreferenceDataStoreHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private var dataStore: PreferenceDataStoreHelper) {
    suspend fun saveUserToken(authorizationKey: String) = withContext(Dispatchers.IO) {
        dataStore.putPreference(USER_TOKEN_PREFERENCE_DATA_STORE_KEY, authorizationKey)
    }

    suspend fun getUserToken(): String = withContext(Dispatchers.IO) {
        dataStore.getFirstPreference(USER_TOKEN_PREFERENCE_DATA_STORE_KEY, GUEST_USER)
    }

    suspend fun deleteUserToken() = withContext(Dispatchers.IO) {
        dataStore.removePreference(USER_TOKEN_PREFERENCE_DATA_STORE_KEY)
    }

    suspend fun saveFCMToken(fcmToken: String) = withContext(Dispatchers.IO) {
        dataStore.putPreference(USER_FCM_TOKEN_PREFERENCE_DATA_STORE_KEY, fcmToken)
    }

    suspend fun getFCNToken(): String = withContext(Dispatchers.IO) {
        dataStore.getFirstPreference(USER_FCM_TOKEN_PREFERENCE_DATA_STORE_KEY, GUEST_USER)
    }

    suspend fun getUserVerifiedState(): Boolean = withContext(Dispatchers.IO) {
        dataStore.getFirstPreference(USER_VERIFIED_PREFERENCE_DATA_STORE_KEY, false)
    }

    suspend fun editUserVerifyState(isVerified: Boolean) = withContext(Dispatchers.IO) {
        dataStore.putPreference(USER_VERIFIED_PREFERENCE_DATA_STORE_KEY, isVerified)
    }
    suspend fun saveUserId(userID: String) = withContext(Dispatchers.IO) {
        dataStore.putPreference(USER_ID_PREFERENCE_DATA_STORE_KEY, userID)
    }
    suspend fun getUserID(): String = withContext(Dispatchers.IO) {
        dataStore.getFirstPreference(USER_ID_PREFERENCE_DATA_STORE_KEY, GUEST_USER)
    }

    suspend fun deleteUserId() = withContext(Dispatchers.IO) {
        dataStore.removePreference(USER_ID_PREFERENCE_DATA_STORE_KEY)
    }
}