package com.fadil.aksarakeun.models.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("createdAt")
    val createdAt: String
)