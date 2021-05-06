package com.example.mypizzasmvi.core.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PizzaModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("priceP") val priceP: Double,
    @SerializedName("priceM") val priceM: Double,
    @SerializedName("priceG") val priceG: Double
) : Parcelable