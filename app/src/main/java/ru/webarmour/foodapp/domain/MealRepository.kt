package ru.webarmour.foodapp.domain

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.webarmour.foodapp.data.room.entity.MealItemDb
import ru.webarmour.foodapp.domain.model.CategoryItem
import ru.webarmour.foodapp.domain.model.MealByCategory
import ru.webarmour.foodapp.domain.model.MealItem

interface MealRepository {

    suspend fun getRandomMeal(): List<MealItem>

    suspend fun getCategoryOfMeals(): List<CategoryItem>

    suspend fun getMealsByCategory(category: String): List<MealByCategory>

    suspend fun getMealsByCategoryScreen(category: String): List<MealByCategory>

    suspend fun getMealById(id: String): MealItem

    suspend fun searchMeals(searchQuery: String): List<MealItem>

    suspend fun insertAndUpdateMeal(mealItem: MealItem)

    suspend fun deleteMealFromDb(mealItem: MealItem)

    fun getAllMeals(): LiveData<List<MealItem>>

}