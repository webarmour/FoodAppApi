package ru.webarmour.foodapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class MealsByCategoryListDto(
    @SerializedName("meals") val meals: List<MealsByCategoryDto>
)