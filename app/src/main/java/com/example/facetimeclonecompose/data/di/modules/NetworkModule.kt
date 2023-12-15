package com.example.facetimeclonecompose.data.di.modules

import com.example.facetimeclonecompose.data.utilities.Constants.GUEST_USER
import com.example.facetimeclonecompose.data.utilities.Constants.USER_TOKEN_PREFERENCE_DATA_STORE_KEY
import com.example.facetimeclonecompose.data.utilities.PreferenceDataStoreHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.header
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(dataStoreHelper: PreferenceDataStoreHelper,coroutineScope: CoroutineScope): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
            install(DefaultRequest) {
                if (!headers.contains("No-Authentication")) {
                    coroutineScope.launch {
                        header(
                            "Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMTI3LjAuMC4xOjgwODAvdXNlciIsImlzcyI6Imh0dHA6Ly8xMjcuMC4wLjE6ODA4MC8iLCJ2ZXJpZmllZCI6dHJ1ZSwiZXhwIjoxNzMzNzE2OTcwLCJ1c2VySWQiOiI2M2Q1YjJjOGRiMzkyOTU0ZWNhZTk3MzcifQ.AUFusEf4YpYR5GcU66xo8_5bSPDbMizoDnYQXnAkrHs"
                        )
                    }
                }
            }
        }
    }
}

//TODO("dataStoreHelper.getFirstPreference(
//                                USER_TOKEN_PREFERENCE_DATA_STORE_KEY,
//                                GUEST_USER
//                            )")