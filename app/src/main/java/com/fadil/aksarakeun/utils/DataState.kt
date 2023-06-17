package com.fadil.aksarakeun.utils

import com.fadil.aksarakeun.data.abstraction.HttpResult
import com.fadil.aksarakeun.data.abstraction.Status

data class DataState<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int?,
    val cause: HttpResult = HttpResult.NOT_DEFINED,
) {
    companion object {
        fun <T> success(data: T): DataState<T> = DataState(
            status = Status.SUCCESS,
            data = data,
            message = null,
            code = null
        )

        fun <T> error(
            data: T? = null,
            message: String? = null,
            code: Int? = null,
            cause: HttpResult = HttpResult.NOT_DEFINED
        ): DataState<T> = DataState(
            status = Status.ERROR,
            data = data,
            message = message,
            code = code,
            cause = cause
        )

        fun <T> loading(data: T? = null): DataState<T> =
            DataState(
                status = Status.LOADING,
                data = data,
                message = null,
                code = null
            )
    }
}
