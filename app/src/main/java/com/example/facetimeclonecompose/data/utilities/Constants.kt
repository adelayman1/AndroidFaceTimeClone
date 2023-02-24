package com.example.facetimeclonecompose.data.utilities

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


object Constants {
    const val BASE_URL:String = "http://127.0.0.1:8080"
    const val USER_ENDPOINT:String = "user"
    val USER_TOKEN_PREFERENCE_DATA_STORE_KEY = stringPreferencesKey("UserToken")
    val USER_VERIFIED_PREFERENCE_DATA_STORE_KEY = booleanPreferencesKey("verified")
    const val GUEST_USER = "guest"
    const val USER_PREFERENCES_DATA_STORE_KEY:String = "user"
}