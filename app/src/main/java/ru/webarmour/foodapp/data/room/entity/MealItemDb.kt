package ru.webarmour.foodapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_entity")
data class MealItemDb(
    val dateModified: Any?,
    @PrimaryKey()
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
    var isFavourite: Boolean = false
)