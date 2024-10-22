package ru.webarmour.foodapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class MealDto(
   @SerializedName("dateModified") val dateModified: Any?,
   @SerializedName("idMeal") val idMeal: String,
   @SerializedName("strArea") val strArea: String,
   @SerializedName("strCategory")val strCategory: String,
   @SerializedName("strCreativeCommonsConfirmed") val strCreativeCommonsConfirmed: Any?,
   @SerializedName("strDrinkAlternate")val strDrinkAlternate: Any?,
   @SerializedName("strImageSource")val strImageSource: Any?,
   @SerializedName("strInstructions")val strInstructions: String,
   @SerializedName("strMeal")val strMeal: String,
   @SerializedName("strMealThumb")val strMealThumb: String,
   @SerializedName("strSource") val strSource: String,
   @SerializedName("strTags") val strTags: String?,
   @SerializedName("strYoutube") val strYoutube: String
)