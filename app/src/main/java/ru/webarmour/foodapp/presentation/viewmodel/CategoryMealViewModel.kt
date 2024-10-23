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
import ru.webarmour.foodapp.domain.model.MealByCategory
import ru.webarmour.foodapp.domain.usecase.GetMealsByCategoryUseCase
import javax.inject.Inject

class CategoryMealViewModel @Inject constructor(
    private val categoryUseCase: GetMealsByCategoryUseCase
) : ViewModel() {

    private val _categoryMeals = MutableLiveData<List<MealByCategory>>()
    val categoryMeals: LiveData<List<MealByCategory>> = _categoryMeals

    fun getMealsByCategory(category: String) {
        viewModelScope.launch {
            categoryUseCase(category)
        }
    }
}