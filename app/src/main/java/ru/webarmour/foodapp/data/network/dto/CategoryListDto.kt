package ru.webarmour.foodapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CategoryListDto(
  @SerializedName("categories") val categories: List<CategoryDto>
)