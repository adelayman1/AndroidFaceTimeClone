package com.adel.facetimeclone.di.module

import com.adel.facetimeclone.data.source.remote.endPoints.FCMCallApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideFCMApiService(retrofit: Retrofit): FCMCallApiService {
        return retrofit.create(FCMCallApiService::class.java)
    }
}