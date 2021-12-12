package com.harera.common.di

import android.app.Application
import android.content.Context
import com.harera.common.local.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalStoreModule {

    companion object {
        @Provides
        @Singleton
        fun provideUserDataStore(application: Application): UserDataStore {
            return UserDataStore(application)
        }
    }
}