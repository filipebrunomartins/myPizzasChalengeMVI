package com.example.mypizzasmvi.core.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PizzaModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("priceP") val priceP: Int,
    @SerializedName("priceM") val priceM: Int,
    @SerializedName("priceG") val priceG: Int
) : Parcelable