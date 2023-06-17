package com.fadil.aksarakeun.base

import com.fadil.aksarakeun.data.abstraction.HttpExceptionError
import com.fadil.aksarakeun.data.abstraction.HttpResult
import com.fadil.aksarakeun.utils.DataState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {
    suspend inline fun <reified T> safeApiCall(
        crossinline apiCall: suspend () -> T
    ): DataState<T> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiCall.invoke()
                DataState.success(result)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> HttpExceptionError().parse(throwable)
                    is UnknownHostException -> DataState.error(
                        data = null,
                        message = "No internet connection",
                        code = null,
                        cause = HttpResult.NO_CONNECTION
                    )

                    is SocketTimeoutException -> DataState.error(
                        data = null,
                        message = "Slow connection",
                        code = null,
                        cause = HttpResult.TIMEOUT
                    )

                    is IOException -> DataState.error(
                        data = null,
                        message = throwable.message.toString(),
                        code = null,
                        cause = HttpResult.BAD_RESPONSE
                    )

                    else -> {
                        DataState.error(
                            data = null,
                            message = throwable.message.toString(),
                            code = null,
                            cause = HttpResult.NOT_DEFINED
                        )
                    }
                }
            }
        }
    }
}