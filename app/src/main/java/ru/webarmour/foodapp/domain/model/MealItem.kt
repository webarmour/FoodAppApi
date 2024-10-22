package ru.webarmour.foodapp.domain.model

data class MealItem(
    val dateModified: Any?,
    val idMeal: String,
    val strArea: String?,
    val strCategory: String?,
    val strCreativeCommonsConfirmed: Any?,
    val strDrinkAlternate: Any?,
    val strImageSource: Any?,
    val strInstructions: String?,
    val strMeal: String?,
    val strMealThumb: String,
    val strSource: String,
    val strTags: String?,
    val strYoutube: String?,
    val isFavourite: Boolean = false
)