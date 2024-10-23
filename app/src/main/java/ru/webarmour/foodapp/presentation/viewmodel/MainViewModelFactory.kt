package ru.webarmour.foodapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.webarmour.foodapp.data.mapper.MapperDtoToDomain
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.domain.usecase.GetCategoryOfMealsUseCase
import ru.webarmour.foodapp.domain.usecase.GetMealByIdUseCase
import ru.webarmour.foodapp.domain.usecase.GetMealsByCategoryUseCase
import ru.webarmour.foodapp.domain.usecase.GetRandomMealUseCase
import ru.webarmour.foodapp.domain.usecase.SearchMealsUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val database: MealDatabase,
    private val searchMealUseCase: SearchMealsUseCase,
    private val categoryOfMealsUseCase: GetCategoryOfMealsUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    private val getRandomMealUseCase: GetRandomMealUseCase,
    private val mapper: MapperDtoToDomain,
    private val mapperDb: MapperDbToDomain,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(database,
                searchMealUseCase,
                categoryOfMealsUseCase,
                getMealByIdUseCase,
                getMealsByCategoryUseCase,
                getRandomMealUseCase,
                mapper,
                mapperDb) as T
        } else throw IllegalStateException("Unknown ViewModel")
    }
}