package ru.webarmour.foodapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.webarmour.foodapp.data.mapper.MapperDtoToDomain
import ru.webarmour.foodapp.data.network.RetrofitInstance
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.domain.model.MealItem

class DetailViewModel(
    val mealDatabase: MealDatabase,
) : ViewModel() {

    private val _detailMealLiveData = MutableLiveData<MealItem>()
    val detailMealLiveData: LiveData<MealItem> = _detailMealLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var mapper = MapperDtoToDomain()
    private val mapperDb = MapperDbToDomain()

    fun getDetailMealById(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            delay(200L)
            val response = RetrofitInstance.api.getMealById(id)
            if (response.isSuccessful && response.body() != null) {
                val meal = response.body()!!.meals.first()
                val mapped = mapper.mapDtoMealToDomain(meal)
                _detailMealLiveData.value = mapped
                _isLoading.value = false
            } else {
                return@launch
            }
        }
    }

    fun insertMeal(mealItem: MealItem) {
        viewModelScope.launch {
            mealDatabase.mealDao().insertAndUpdateMeal(mapperDb.mapDomainItemToDbItem(mealItem))
        }
    }



}