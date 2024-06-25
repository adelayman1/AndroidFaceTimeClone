package com.example.facetimeclonecompose.data.di.modules

import com.example.facetimeclonecompose.data.sources.local.dataSources.UserLocalDataSource
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

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideHttpClient(
        localDataSource: UserLocalDataSource,
        coroutineScope: CoroutineScope
    ): HttpClient {
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
                            "Authorization",
                            "Bearer ${localDataSource.getUserToken()}"
                        )
                    }
                }
            }
        }
    }
}