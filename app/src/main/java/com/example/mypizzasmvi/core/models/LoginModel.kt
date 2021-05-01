package com.example.mypizzasmvi.core.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
) : Parcelable

data class LoginResponse (
    @SerializedName("access_token") val access_token : String,
    @SerializedName("token_type") val token_type : String
)


