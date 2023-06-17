package com.fadil.aksarakeun.repository

import com.fadil.aksarakeun.base.BaseRepository
import com.fadil.aksarakeun.data.network.AuthApiService
import com.fadil.aksarakeun.models.error.LoginErrorResponse
import com.fadil.aksarakeun.models.error.RegisterErrorResponse
import com.fadil.aksarakeun.models.request.LoginRequest
import com.fadil.aksarakeun.models.request.RegisterRequest
import com.fadil.aksarakeun.models.response.BaseApiResponse
import com.fadil.aksarakeun.models.response.UserModel
import com.fadil.aksarakeun.models.response.RegisterResponse
import com.fadil.aksarakeun.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
) : BaseRepository() {
    suspend fun register(registerRequest: RegisterRequest): Flow<DataState<BaseApiResponse<RegisterResponse, RegisterErrorResponse>>> {
        return flow {
            emit(DataState.loading())
            emit(safeApiCall { authApiService.register(registerRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun login(loginRequest: LoginRequest): Flow<DataState<BaseApiResponse<UserModel, LoginErrorResponse>>> {
        return flow {
            emit(DataState.loading())
            emit(safeApiCall { authApiService.login(loginRequest) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun refreshToken(): Flow<DataState<BaseApiResponse<UserModel, Nothing>>> {
        return flow {
            emit(DataState.loading())
            emit(safeApiCall { authApiService.refreshToken() })
        }.flowOn(Dispatchers.IO)
    }
}