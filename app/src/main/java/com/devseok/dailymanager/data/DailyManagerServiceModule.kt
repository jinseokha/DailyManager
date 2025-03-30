package com.devseok.dailymanager.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DailyManagerServiceModule {

    @Provides
    @Singleton
    fun provideDailyManager(@Named("dailymanager") retrofit: Retrofit): DailyManagerService =
        retrofit.create(
            DailyManagerService::class.java
        )

}