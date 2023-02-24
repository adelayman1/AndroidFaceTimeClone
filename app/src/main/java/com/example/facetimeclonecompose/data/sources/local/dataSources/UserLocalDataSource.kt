package com.example.facetimeclonecompose.data.sources.local.dataSources

import com.example.facetimeclonecompose.data.utilities.Constants.GUEST_USER
import com.example.facetimeclonecompose.data.utilities.Constants.USER_TOKEN_PREFERENCE_DATA_STORE_KEY
import com.example.facetimeclonecompose.data.utilities.Constants.USER_VERIFIED_PREFERENCE_DATA_STORE_KEY
import com.example.facetimeclonecompose.data.utilities.PreferenceDataStoreHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(var dataStore: PreferenceDataStoreHelper) {
    suspend fun saveUserToken(authorizationKey: String) = withContext(Dispatchers.IO) {
        dataStore.putPreference(USER_TOKEN_PREFERENCE_DATA_STORE_KEY, authorizationKey)
    }

    suspend fun getUserToken(): String = withContext(Dispatchers.IO) {
        dataStore.getFirstPreference(USER_TOKEN_PREFERENCE_DATA_STORE_KEY, GUEST_USER)
    }

    suspend fun deleteUserToken() = withContext(Dispatchers.IO) {
        dataStore.removePreference(USER_TOKEN_PREFERENCE_DATA_STORE_KEY)
    }

    suspend fun getUserVerifiedState(): Boolean = withContext(Dispatchers.IO) {
        dataStore.getFirstPreference(USER_VERIFIED_PREFERENCE_DATA_STORE_KEY, false)
    }

    suspend fun editUserVerifyState(isVerified: Boolean) = withContext(Dispatchers.IO) {
        dataStore.putPreference(USER_VERIFIED_PREFERENCE_DATA_STORE_KEY, isVerified)
    }
}