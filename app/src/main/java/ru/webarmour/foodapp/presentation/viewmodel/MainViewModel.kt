package ru.webarmour.foodapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.webarmour.foodapp.data.mapper.MapperDtoToDomain
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.data.room.entity.MealItemDb
import ru.webarmour.foodapp.domain.model.CategoryItem
import ru.webarmour.foodapp.domain.model.MealByCategory
import ru.webarmour.foodapp.domain.model.MealItem
import ru.webarmour.foodapp.domain.usecase.GetCategoryOfMealsUseCase
import ru.webarmour.foodapp.domain.usecase.GetMealByIdUseCase
import ru.webarmour.foodapp.domain.usecase.GetMealsByCategoryUseCase
import ru.webarmour.foodapp.domain.usecase.SearchMealsUseCase

class MainViewModel(
    private val database: MealDatabase,
    private val searchMealUseCase: SearchMealsUseCase,
    private val categoryOfMealsUseCase: GetCategoryOfMealsUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,

    ) : ViewModel() {

    private val _randomMealLiveData = MutableLiveData<MealItem>()
    val randomMealLiveData: LiveData<MealItem> = _randomMealLiveData

    private val _mealByIdLiveData = MutableLiveData<MealItem>()
    val mealByIdLiveData: LiveData<MealItem> = _mealByIdLiveData

    private val _randomListMealLiveData = MutableLiveData<List<MealByCategory>>()
    val randomListMealLiveData: LiveData<List<MealByCategory>> = _randomListMealLiveData

    private val _categoryItem = MutableLiveData<List<CategoryItem>>()
    val categoryItem: LiveData<List<CategoryItem>> = _categoryItem

    private val _favouritesMealLiveData = database.mealDao().getAllMeals()
    val favouritesMealLiveData: LiveData<List<MealItemDb>> = _favouritesMealLiveData

    private val _searchedMealLiveData = MutableLiveData<List<MealItem>>()
    val searchedMealLiveData: LiveData<List<MealItem>> = _searchedMealLiveData

    private val mapper = MapperDtoToDomain()
    private val mapperDb = MapperDbToDomain()


    fun searchMeals(searchQuery: String) {
        viewModelScope.launch {
            searchMealUseCase(searchQuery)
        }
    }


    fun getCategoriesOfMeals() {
        viewModelScope.launch {
            categoryOfMealsUseCase()
        }
    }

    fun getMealById(id: String) {
        viewModelScope.launch {
            getMealByIdUseCase(id)
        }
    }

    fun deleteMeal(mealItem: MealItem) {
        viewModelScope.launch {
            database.mealDao().delete(mapperDb.mapDomainItemToDbItem(mealItem))
        }
    }

    fun insertMeal(mealItem: MealItem) {
        viewModelScope.launch {
            database.mealDao().insertAndUpdateMeal(mapperDb.mapDomainItemToDbItem(mealItem))
        }
    }

    private fun getRandomCategory(): String {
        return listOf(
            "Seafood",
            "Pork",
            "Beef",
            "Chicken",
            "Dessert",
            "Lamb",
            "Miscellaneous",
            "Pasta",
            "Side",
            "Starter",
            "Breakfast",
            "Goat"
        ).random()
    }

    fun getListRandomMeal() {
        viewModelScope.launch {
            getMealsByCategoryUseCase(getRandomCategory())
        }
    }

}