package ru.webarmour.foodapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.domain.usecase.GetMealByIdUseCase
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(
    val mealDatabase: MealDatabase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val mapperDb: MapperDbToDomain,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mealDatabase, getMealByIdUseCase, mapperDb) as T
        } else throw IllegalStateException("Unknown ViewModel")
    }
}