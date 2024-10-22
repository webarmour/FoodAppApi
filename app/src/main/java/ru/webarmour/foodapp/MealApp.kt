package ru.webarmour.foodapp

import android.app.Application
import ru.webarmour.foodapp.data.room.MealDatabase

class MealApp: Application() {

    private lateinit var _mealDatabase: MealDatabase

    val mealDatabase: MealDatabase
        get() = _mealDatabase

    override fun onCreate() {
        super.onCreate()
        _mealDatabase = MealDatabase.getInstance(this)
    }
}