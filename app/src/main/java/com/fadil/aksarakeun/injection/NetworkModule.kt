package com.fadil.aksarakeun.injection

import com.fadil.aksarakeun.BuildConfig
import com.fadil.aksarakeun.data.network.AuthApiService
import com.fadil.aksarakeun.data.network.MainApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Singleton
    @Provides
    fun providesOkHttpClientNotAuthenticate(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val timeoutDuration = 60L
        return OkHttpClient
            .Builder()
            .connectTimeout(timeoutDuration, TimeUnit.SECONDS)
            .readTimeout(timeoutDuration, TimeUnit.SECONDS)
            .writeTimeout(timeoutDuration, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClientNotAuthenticate(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideMainApiService(retrofit: Retrofit): MainApiService =
        retrofit.create(MainApiService::class.java)
}