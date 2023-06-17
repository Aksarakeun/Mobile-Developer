package com.fadil.aksarakeun.data.network

import com.fadil.aksarakeun.models.error.LoginErrorResponse
import com.fadil.aksarakeun.models.request.LoginRequest
import com.fadil.aksarakeun.models.response.BaseApiResponse
import com.fadil.aksarakeun.models.response.HistoryResponse
import com.fadil.aksarakeun.models.response.UserModel
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MainApiService {
    @GET("/user/current-user")
    suspend fun profile(@Header("Authorization") accessToken: String): BaseApiResponse<UserModel, String>

    @Multipart
    @POST("/predict")
    suspend fun predict(
        @Header("Authorization") accessToken: String,
        @Part file: MultipartBody.Part,
    ): BaseApiResponse<String, String>


    @GET("/user/history")
    suspend fun history(@Header("Authorization") accessToken: String): BaseApiResponse<List<HistoryResponse>, String>


}