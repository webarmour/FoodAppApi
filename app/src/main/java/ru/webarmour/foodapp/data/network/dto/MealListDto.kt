package ru.webarmour.foodapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class MealListDto(
   @SerializedName("meals") val meals: List<MealDto>
)