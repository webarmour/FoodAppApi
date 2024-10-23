package ru.webarmour.foodapp

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import ru.webarmour.foodapp.data.room.MealDatabase
import javax.inject.Singleton

@HiltAndroidApp
class MealApp: Application() {


    override fun onCreate() {
        super.onCreate()
    }
}