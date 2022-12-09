package com.adel.facetimeclone.di.module

import com.adel.facetimeclone.data.repository.RoomRepositoryImpl
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.repository.RoomRepository
import com.adel.facetimeclone.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository {
        return roomRepositoryImpl
    }
    @Singleton
    @Provides
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository {
        return userRepositoryImpl
    }
}