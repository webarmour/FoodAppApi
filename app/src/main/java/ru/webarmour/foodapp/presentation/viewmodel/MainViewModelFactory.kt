package ru.webarmour.foodapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.webarmour.foodapp.data.room.MealDatabase

class MainViewModelFactory(
    val mealDatabase: MealDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(mealDatabase) as T
        } else throw IllegalStateException("Unknown ViewModel")
    }
}