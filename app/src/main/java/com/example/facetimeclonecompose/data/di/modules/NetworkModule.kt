package com.example.facetimeclonecompose.data.di.modules

import android.content.SharedPreferences
import com.example.facetimeclonecompose.data.utilities.Constants.USER_TOKEN_SHARED_PREFERENCES_KEY
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(prefs: SharedPreferences): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
            install(DefaultRequest) {
                if (!headers.contains("No-Authentication")) {
                    header("Authorization", (prefs.getString(USER_TOKEN_SHARED_PREFERENCES_KEY, null) ?: ""))
                }
            }
        }
    }
}