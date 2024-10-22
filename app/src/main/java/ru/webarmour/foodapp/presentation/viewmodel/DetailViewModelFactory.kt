package ru.webarmour.foodapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.webarmour.foodapp.data.room.MealDatabase

class DetailViewModelFactory(
    val mealDatabase: MealDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mealDatabase) as T
        } else throw IllegalStateException("Unknown ViewModel")
    }
}