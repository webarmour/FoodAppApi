package ru.webarmour.foodapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.domain.model.MealItem
import ru.webarmour.foodapp.domain.usecase.GetMealByIdUseCase

class DetailViewModel(
    private val mealDatabase: MealDatabase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val mapperDb: MapperDbToDomain,
) : ViewModel() {

    private val _detailMealLiveData = MutableLiveData<MealItem?>()
    val detailMealLiveData: MutableLiveData<MealItem?> = _detailMealLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailMealById(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            _detailMealLiveData.value = getMealByIdUseCase(id)
        }
    }

    fun insertMeal(mealItem: MealItem) {
        viewModelScope.launch {
            mealDatabase.mealDao().insertAndUpdateMeal(mapperDb.mapDomainItemToDbItem(mealItem))
        }
    }


}