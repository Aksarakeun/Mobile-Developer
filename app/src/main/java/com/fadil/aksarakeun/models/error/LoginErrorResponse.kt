package com.fadil.aksarakeun.models.error

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(
    @SerializedName("email") val email: List<String>? = null,
    @SerializedName("password") val password: List<String>? = null
)