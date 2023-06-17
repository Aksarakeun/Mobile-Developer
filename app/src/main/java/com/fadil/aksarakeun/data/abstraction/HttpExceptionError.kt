package com.fadil.aksarakeun.data.abstraction

import android.util.Log
import com.fadil.aksarakeun.utils.DataState
import com.fadil.aksarakeun.utils.fromJson
import com.google.gson.Gson
import retrofit2.HttpException
class HttpExceptionError {
    inline fun <reified T> parse(throwable: HttpException): DataState<T> {
        return when (throwable.code()) {
            in 400..451 -> {
                val resource = try {
                    val errorBody = throwable.response()?.errorBody()?.charStream()?.readText()
                        ?: "Unknown HTTP error body"
                    val errorResponse = Gson().fromJson<T>(errorBody)
                    DataState.error(
                        data = errorResponse,
                        message = null,
                        code = throwable.code(),
                        cause = HttpResult.CLIENT_ERROR
                    )
                } catch (e: Exception) {
                    DataState.error(
                        data = null,
                        message = e.message.toString(),
                        code = throwable.code(),
                        cause = HttpResult.CLIENT_ERROR
                    )
                }
                resource
            }

            in 500..599 -> DataState.error(
                data = null,
                message = "Server error",
                code = throwable.code(),
                cause = HttpResult.SERVER_ERROR
            )

            else -> DataState.error(
                data = null,
                message = "Undefined error",
                code = throwable.code(),
                cause = HttpResult.NOT_DEFINED
            )
        }
    }
}