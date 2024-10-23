package ru.webarmour.foodapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.data.room.entity.MealDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMealDatabase(@ApplicationContext context: Context): MealDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MealDatabase::class.java,
            "meal_db"
        ).build()
    }

    @Provides
    fun provideMealDao(database: MealDatabase): MealDao {
        return database.mealDao()
    }
}