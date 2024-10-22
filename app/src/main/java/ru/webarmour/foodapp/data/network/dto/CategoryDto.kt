package ru.webarmour.foodapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("idCategory") val idCategory: String,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("strCategoryDescription") val strCategoryDescription: String,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String,
)