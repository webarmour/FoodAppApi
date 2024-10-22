package ru.webarmour.foodapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.webarmour.foodapp.data.mapper.MapperDtoToDomain
import ru.webarmour.foodapp.data.network.RetrofitInstance
import ru.webarmour.foodapp.domain.model.CategoryItem
import ru.webarmour.foodapp.domain.model.MealByCategory

class CategoryMealViewModel : ViewModel() {

    private val _categoryMeals = MutableLiveData<List<MealByCategory>>()
    val categoryMeals: LiveData<List<MealByCategory>> = _categoryMeals

    private var mapper = MapperDtoToDomain()

    fun getMealsByCategory(category: String) {

        viewModelScope.launch {
            delay(200L)
            val response = RetrofitInstance
                .api
                .getMealsByCategoryScreen(category)
            if (response.isSuccessful && response.body() != null) {
                Log.d("TEST_TAG", "${response.body()}")
                Log.d("TEST_TAG", "${response.message()}")
                val meal = response.body()!!.meals
                val mapped = mapper.mapCategoryListDtoToDomain(meal)
                _categoryMeals.value = mapped

            } else {
                return@launch
            }
        }

    }

}