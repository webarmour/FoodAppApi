package ru.webarmour.foodapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.webarmour.foodapp.data.mapper.MapperDtoToDomain
import ru.webarmour.foodapp.data.network.RetrofitInstance
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.data.room.entity.MealItemDb
import ru.webarmour.foodapp.domain.model.CategoryItem
import ru.webarmour.foodapp.domain.model.MealByCategory
import ru.webarmour.foodapp.domain.model.MealItem

class MainViewModel(
    private val database: MealDatabase,
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


    fun searchMeals(searchQuery: String){
        viewModelScope.launch {
            Log.d("SearchFragment", "Coroutine started")
            try {
                val response = RetrofitInstance.api.searchMeals(searchQuery)
                Log.d("MEAL_TAG", "Response Body: ${response.body()}")
                Log.d("MEAL_TAG", "${response.body()}")
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val meal = response.body()!!.meals
                        val mapped = mapper.mapListDtoMealToDomain(meal)
                        _searchedMealLiveData.postValue(mapped)
                        Log.d("MEAL_TAG", "${_searchedMealLiveData.value}")
                    } else {
                        Log.d("SearchFragment", "Failure in else Block")
                        return@withContext
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("SearchFragment", "Failure: ${e.message}")
                }
            }
        }
    }



    fun getCategoriesOfMeals() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategoryOfMeals()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val meal = response.body()!!.categories
                        val mapped = mapper.mapListCategoriesDtoToDomain(meal)
                        _categoryItem.postValue(mapped)
                    } else {
                        return@withContext
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("TEST_TAG", "Failure: ${e.message}")
                }
            }
        }
    }

    fun getMealById(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMealById(id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val meal = response.body()!!.meals.first()
                        val mapped = mapper.mapDtoMealToDomain(meal)
                        _mealByIdLiveData.postValue(mapped)
                    } else {
                        return@withContext
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("TEST_TAG", "Failure: ${e.message}")
                }
            }


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

    fun getRandomMeal() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomMeal()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val meal = response.body()!!.meals.first()
                        val mapped = mapper.mapDtoMealToDomain(meal)
                        _randomMealLiveData.value = mapped
                    } else {
                        return@withContext
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("TEST_TAG", "Failure: ${e.message}")
                }
            }
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
            try {
                val response = RetrofitInstance.api.getMealsByCategory(getRandomCategory())
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val meal = response.body()!!.meals
                        val mapped = mapper.mapCategoryListDtoToDomain(meal)
                        _randomListMealLiveData.value = mapped
                    } else {
                        return@withContext
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("TEST_TAG", "Failure: ${e.message}")
                }
            }
        }
    }

}