package com.fadil.aksarakeun.data.network

import com.fadil.aksarakeun.models.error.LoginErrorResponse
import com.fadil.aksarakeun.models.error.RegisterErrorResponse
import com.fadil.aksarakeun.models.request.LoginRequest
import com.fadil.aksarakeun.models.request.RegisterRequest
import com.fadil.aksarakeun.models.response.BaseApiResponse
import com.fadil.aksarakeun.models.response.UserModel
import com.fadil.aksarakeun.models.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): BaseApiResponse<UserModel, LoginErrorResponse>

    @POST("/user/refresh-token")
    suspend fun refreshToken(): BaseApiResponse<UserModel, Nothing>

    @POST("/user/signup")
    suspend fun register(@Body signInRequest: RegisterRequest): BaseApiResponse<RegisterResponse, RegisterErrorResponse>
}