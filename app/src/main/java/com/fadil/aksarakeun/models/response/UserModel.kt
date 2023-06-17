package com.fadil.aksarakeun.models.response

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("verified") var verified: Boolean,
    @SerializedName("active") var active: Boolean,
    @SerializedName("token") var token: String,
    @SerializedName("refresh_token") var refreshToken: String
)