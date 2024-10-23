package ru.webarmour.foodapp.data

import android.util.Log
import ru.webarmour.foodapp.data.mapper.MapperDtoToDomain
import ru.webarmour.foodapp.data.network.RetrofitInstance
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.domain.MealRepository
import ru.webarmour.foodapp.domain.model.CategoryItem
import ru.webarmour.foodapp.domain.model.MealByCategory
import ru.webarmour.foodapp.domain.model.MealItem
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mapperDbToDomain: MapperDbToDomain,
    private val mapperDtoToDomain: MapperDtoToDomain,

): MealRepository {

    override suspend fun getRandomMeal(): List<MealItem> {
       return try {
           val response = RetrofitInstance.api.getRandomMeal()
           if (response.isSuccessful){
               val meals = response.body()?.meals ?: emptyList()
               mapperDtoToDomain.mapListDtoMealToDomain(meals)
           } else {
               Log.d("Retrofit error", "${response.message()} ${response.errorBody()}")
               emptyList()
           }

       } catch (e: Exception){
           Log.d("Retrofit error", "${e.message}")
           emptyList()
       }
    }

    override suspend fun getCategoryOfMeals(): List<CategoryItem> {

        return try {
            val response = RetrofitInstance.api.getCategoryOfMeals()
            if (response.isSuccessful){
                val meals = response.body()?.categories ?: emptyList()
                mapperDtoToDomain.mapListCategoriesDtoToDomain(meals)
            } else {
                Log.d("Retrofit error", "${response.message()} ${response.errorBody()}")
                emptyList()
            }
        } catch (e: Exception){
            Log.d("Retrofit error", "${e.message}")
            emptyList()
        }
    }

    override suspend fun getMealsByCategory(category: String): List<MealByCategory> {
        return try {
            val response = RetrofitInstance.api.getMealsByCategory(category)
            if (response.isSuccessful){
                val meals = response.body()?.meals ?: emptyList()
                mapperDtoToDomain.mapCategoryListDtoToDomain(meals)
            } else {
                Log.d("Retrofit error", "${response.message()} ${response.errorBody()}")
                emptyList()
            }
        } catch (e: Exception){
            Log.d("Retrofit error", "${e.message}")
            emptyList()
        }
    }

    override suspend fun getMealsByCategoryScreen(category: String): List<MealByCategory> {
        return try {
            val response = RetrofitInstance.api.getMealsByCategoryScreen(category)
            if (response.isSuccessful){
                val meals = response.body()?.meals ?: emptyList()
                mapperDtoToDomain.mapCategoryListDtoToDomain(meals)
            } else {
                Log.d("Retrofit error", "${response.message()} ${response.errorBody()}")
                emptyList()
            }
        } catch (e: Exception){
            Log.d("Retrofit error", "${e.message}")
            emptyList()
        }
    }

    override suspend fun getMealById(id: String): MealItem {
        return try {
            val response = RetrofitInstance.api.getMealById(id)
            if (response.isSuccessful){
                val meals = response.body()?.meals ?: emptyList()
                mapperDtoToDomain.mapListDtoMealToDomain(meals).first()
            } else {
                Log.d("Retrofit error", "${response.message()} ${response.errorBody()}")
               return throw Exception("Unknown item")
            }
        } catch (e: Exception){
            Log.d("Retrofit error", "${e.message}")
            throw Exception(e)
        }
    }

    override suspend fun searchMeals(searchQuery: String): List<MealItem> {
        return try {
            val response =  RetrofitInstance.api.searchMeals(searchQuery)
            if (response.isSuccessful){
                val meals = response.body()?.meals ?: emptyList()
                mapperDtoToDomain.mapListDtoMealToDomain(meals)
            } else {
                Log.d("Retrofit error", "${response.message()} ${response.errorBody()}")
                return throw Exception("Unknown item")
            }
        } catch (e: Exception){
            Log.d("Retrofit error", "${e.message}")
            throw Exception(e)
        }

    }
}