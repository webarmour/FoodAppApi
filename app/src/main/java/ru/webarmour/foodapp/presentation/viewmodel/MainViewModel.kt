package ru.webarmour.foodapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.webarmour.foodapp.data.mapper.MapperDtoToDomain
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.data.room.entity.MealItemDb
import ru.webarmour.foodapp.domain.model.CategoryItem
import ru.webarmour.foodapp.domain.model.MealByCategory
import ru.webarmour.foodapp.domain.model.MealItem
import ru.webarmour.foodapp.domain.usecase.DeleteMealFromDbUseCase
import ru.webarmour.foodapp.domain.usecase.GetAllMealsFromDbUseCase
import ru.webarmour.foodapp.domain.usecase.GetCategoryOfMealsUseCase
import ru.webarmour.foodapp.domain.usecase.GetMealByIdUseCase
import ru.webarmour.foodapp.domain.usecase.GetMealsByCategoryUseCase
import ru.webarmour.foodapp.domain.usecase.GetRandomMealUseCase
import ru.webarmour.foodapp.domain.usecase.InsertItemAndUpdateDbUseCase
import ru.webarmour.foodapp.domain.usecase.SearchMealsUseCase

class MainViewModel(
    private val searchMealUseCase: SearchMealsUseCase,
    private val categoryOfMealsUseCase: GetCategoryOfMealsUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    private val getRandomMealUseCase: GetRandomMealUseCase,
    private val deleteMealFromDbUseCase: DeleteMealFromDbUseCase,
    private val getAllMealsFromDbUseCase: GetAllMealsFromDbUseCase,
    private val insertItemAndUpdateDbUseCase: InsertItemAndUpdateDbUseCase

    ) : ViewModel() {

    private val _randomMealLiveData = MutableLiveData<MealItem>()
    val randomMealLiveData: LiveData<MealItem> = _randomMealLiveData

    private val _mealByIdLiveData = MutableLiveData<MealItem?>()
    val mealByIdLiveData: MutableLiveData<MealItem?> = _mealByIdLiveData

    private val _randomListMealLiveData = MutableLiveData<List<MealByCategory>?>()
    val randomListMealLiveData: LiveData<List<MealByCategory>?> = _randomListMealLiveData

    private val _categoryItem = MutableLiveData<List<CategoryItem>?>()
    val categoryItem: MutableLiveData<List<CategoryItem>?> = _categoryItem

    private val _favouritesMealLiveData = getAllMealsFromDbUseCase()
    val favouritesMealLiveData: LiveData<List<MealItem>> = _favouritesMealLiveData

    private val _searchedMealLiveData = MutableLiveData<List<MealItem>?>()
    val searchedMealLiveData: MutableLiveData<List<MealItem>?> = _searchedMealLiveData

    fun getRandomMeal() {
        viewModelScope.launch {
            _randomMealLiveData.value = getRandomMealUseCase().toList().first()
        }
    }

    fun searchMeals(searchQuery: String) {
        viewModelScope.launch {
            _searchedMealLiveData.value = searchMealUseCase(searchQuery)
        }
    }


    fun getCategoriesOfMeals() {
        viewModelScope.launch {
           _categoryItem.value = categoryOfMealsUseCase()
        }
    }

    fun getMealById(id: String) {
        viewModelScope.launch {
            _mealByIdLiveData.value = getMealByIdUseCase(id)
        }
    }

    fun deleteMeal(mealItem: MealItem) {
        viewModelScope.launch {
            deleteMealFromDbUseCase(mealItem)
        }
    }

    fun insertMeal(mealItem: MealItem) {
        viewModelScope.launch {
            insertItemAndUpdateDbUseCase(mealItem)
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
            _randomListMealLiveData.value =  getMealsByCategoryUseCase(getRandomCategory())
        }
    }

}