package ru.webarmour.foodapp.data.mapper

import ru.webarmour.foodapp.data.network.dto.CategoryDto
import ru.webarmour.foodapp.data.network.dto.MealsByCategoryDto
import ru.webarmour.foodapp.data.network.dto.MealDto
import ru.webarmour.foodapp.domain.model.MealByCategory
import ru.webarmour.foodapp.domain.model.MealItem
import ru.webarmour.foodapp.domain.model.CategoryItem

class MapperDtoToDomain {


    fun mapDtoMealToDomain(mealDto: MealDto) = MealItem(
        idMeal = mealDto.idMeal,
        strArea = mealDto.strArea,
        strMeal = mealDto.strMeal,
        dateModified = mealDto.dateModified,
        strCategory = mealDto.strCategory,
        strCreativeCommonsConfirmed = mealDto.strCreativeCommonsConfirmed,
        strDrinkAlternate = mealDto.strDrinkAlternate,
        strImageSource = mealDto.strImageSource,
        strInstructions = mealDto.strInstructions,
        strMealThumb = mealDto.strMealThumb,
        strSource = mealDto.strSource,
        strTags = mealDto.strTags,
        strYoutube = mealDto.strYoutube
    )

    fun mapListDtoMealToDomain(mealDto:List<MealDto>): List<MealItem> {
       return mealDto.map {
            mapDtoMealToDomain(it)
        }
    }

    fun mapCategoryMealToDtoMeal(mealsByCategoryDto: MealsByCategoryDto) = MealByCategory(
        idMeal = mealsByCategoryDto.idMeal,
        strMeal = mealsByCategoryDto.strMeal,
        strMealThumb = mealsByCategoryDto.strMealThumb
    )

    fun mapCategoryListDtoToDomain(mealsByCategoryDto: List<MealsByCategoryDto>): List<MealByCategory> {
        return mealsByCategoryDto.map {
            mapCategoryMealToDtoMeal(it)
        }
    }

    fun mapCategoriesDtoToDomain(categoriesMeal: CategoryDto) = CategoryItem(
        idCategory = categoriesMeal.idCategory,
        strCategory = categoriesMeal.strCategory,
        strCategoryDescription = categoriesMeal.strCategoryDescription,
        strCategoryThumb = categoriesMeal.strCategoryThumb
    )
    fun mapListCategoriesDtoToDomain(categoriesDtoList: List<CategoryDto>): List<CategoryItem> {
        return categoriesDtoList.map {
            mapCategoriesDtoToDomain(it)
        }
    }



}