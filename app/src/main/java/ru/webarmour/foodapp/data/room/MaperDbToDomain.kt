package ru.webarmour.foodapp.data.room

import androidx.lifecycle.LiveData
import ru.webarmour.foodapp.data.room.entity.MealItemDb
import ru.webarmour.foodapp.domain.model.MealItem
import javax.inject.Inject

class MapperDbToDomain @Inject constructor(){


    fun mapDomainItemToDbItem(mealItem: MealItem) = MealItemDb(
        dateModified = mealItem.dateModified,
        idMeal = mealItem.idMeal,
        strArea = mealItem.strArea,
        strCategory = mealItem.strCategory,
        strCreativeCommonsConfirmed = mealItem.strCreativeCommonsConfirmed,
        strDrinkAlternate = mealItem.strDrinkAlternate,
        strImageSource = mealItem.strImageSource,
        strInstructions = mealItem.strInstructions,
        strMeal = mealItem.strMeal,
        strMealThumb = mealItem.strMealThumb ?: "",
        strSource = mealItem.strSource ?: "",
        strTags = mealItem.strTags,
        strYoutube = mealItem.strYoutube,
        isFavourite = mealItem.isFavourite
    )
    fun mapDbItemToDomain(mealItem: MealItemDb) = MealItem(
        dateModified = mealItem.dateModified,
        idMeal = mealItem.idMeal,
        strArea = mealItem.strArea,
        strCategory = mealItem.strCategory,
        strCreativeCommonsConfirmed = mealItem.strCreativeCommonsConfirmed,
        strDrinkAlternate = mealItem.strDrinkAlternate,
        strImageSource = mealItem.strImageSource,
        strInstructions = mealItem.strInstructions,
        strMeal = mealItem.strMeal,
        strMealThumb = mealItem.strMealThumb,
        strSource = mealItem.strSource,
        strTags = mealItem.strTags,
        strYoutube = mealItem.strYoutube,
        isFavourite = mealItem.isFavourite
    )

    fun mapListDbToDomain(mealItemDb: List<MealItemDb>) = mealItemDb.map {
        mapDbItemToDomain(it)
    }


}