package com.devseok.dailymanager.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Ha Jin Seok
 * @created 2025-04-23
 * @desc
 */
@Module
@InstallIn(SingletonComponent::class)
object DailyManagerApiModule {
    private val testSearchServerAddr: String
        get() = "https://test.com/"


    @Singleton
    @Provides
    @Named("dailymanager")
    fun provideDailyManager(): Retrofit =
        Retrofit.Builder()
            .baseUrl(testSearchServerAddr)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
}