package com.fadil.aksarakeun.models.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password: String
)