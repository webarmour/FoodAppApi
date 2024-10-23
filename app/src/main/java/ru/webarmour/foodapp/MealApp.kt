package ru.webarmour.foodapp

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import ru.webarmour.foodapp.data.room.MealDatabase
@HiltAndroidApp
class MealApp: Application() {

    private lateinit var _mealDatabase: MealDatabase

    val mealDatabase: MealDatabase
        get() = _mealDatabase

    override fun onCreate() {
        super.onCreate()
        _mealDatabase = MealDatabase.getInstance(this)
    }
}