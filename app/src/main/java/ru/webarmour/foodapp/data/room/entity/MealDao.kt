package ru.webarmour.foodapp.data.room.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndUpdateMeal(mealItemDb: MealItemDb)

    @Delete
    suspend fun delete(mealItemDb: MealItemDb)

    @Query("SELECT * FROM meal_entity")
    fun getAllMeals():LiveData<List<MealItemDb>>

}