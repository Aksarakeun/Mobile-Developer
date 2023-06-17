package com.fadil.aksarakeun.injection

import android.content.Context
import com.fadil.aksarakeun.data.network.AuthApiService
import com.fadil.aksarakeun.data.network.MainApiService
import com.fadil.aksarakeun.data.prefrences.UserPreferences
import com.fadil.aksarakeun.repository.AuthRepository
import com.fadil.aksarakeun.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesAuthRepository(api: AuthApiService): AuthRepository {
        return AuthRepository(api)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun providesMainRepository(api: MainApiService): MainRepository {
        return MainRepository(api)
    }
}