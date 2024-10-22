package ru.webarmour.foodapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class MealsByCategoryDto(
    @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strMeal")  val strMeal: String,
    @SerializedName("strMealThumb") val strMealThumb: String
)