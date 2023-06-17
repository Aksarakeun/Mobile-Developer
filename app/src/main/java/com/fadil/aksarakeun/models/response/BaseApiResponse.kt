package com.fadil.aksarakeun.models.response

import com.google.gson.annotations.SerializedName

data class BaseApiResponse<D, E>(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("errors") val errorResponse: E? = null,
    @SerializedName("data") val data: D? = null
)