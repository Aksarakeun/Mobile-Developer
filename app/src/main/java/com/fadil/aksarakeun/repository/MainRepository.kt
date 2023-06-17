package com.fadil.aksarakeun.repository

import com.fadil.aksarakeun.base.BaseRepository
import com.fadil.aksarakeun.data.network.MainApiService
import com.fadil.aksarakeun.models.response.BaseApiResponse
import com.fadil.aksarakeun.models.response.HistoryResponse
import com.fadil.aksarakeun.models.response.UserModel
import com.fadil.aksarakeun.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService
) : BaseRepository() {
    suspend fun profile(token: String): Flow<DataState<BaseApiResponse<UserModel, String>>> {
        return flow {
            emit(DataState.loading())
            emit(safeApiCall { mainApiService.profile(token) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun scan(
        token: String,
        image: MultipartBody.Part,
    ): Flow<DataState<BaseApiResponse<String, String>>> {
        return flow {
            emit(DataState.loading())
            emit(safeApiCall { mainApiService.predict(token, image) })
        }
    }

    suspend fun history(token: String): Flow<DataState<BaseApiResponse<List<HistoryResponse>, String>>> {
        return flow {
            emit(DataState.loading())
            emit(safeApiCall { mainApiService.history(token) })
        }
    }
}