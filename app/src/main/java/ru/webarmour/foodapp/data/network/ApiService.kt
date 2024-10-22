package ru.webarmour.foodapp.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.webarmour.foodapp.data.network.dto.CategoryListDto
import ru.webarmour.foodapp.data.network.dto.MealsByCategoryListDto
import ru.webarmour.foodapp.data.network.dto.MealListDto

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealListDto>

    @GET("categories.php")
    suspend fun getCategoryOfMeals(): Response<CategoryListDto>

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") categoryName: String
    ): Response<MealsByCategoryListDto>

    @GET("filter.php")
    suspend fun getMealsByCategoryScreen(
        @Query("c") categoryName: String
    ): Response<MealsByCategoryListDto>

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ): Response<MealListDto>

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") search: String
    ): Response<MealListDto>
}