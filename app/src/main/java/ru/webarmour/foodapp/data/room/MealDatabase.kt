package ru.webarmour.foodapp.data.room

import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.webarmour.foodapp.data.room.entity.MealDao
import ru.webarmour.foodapp.data.room.entity.MealItemDb


@Database(entities = [MealItemDb::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase: RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        var INSTANCE:MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context = context,
                    klass = MealDatabase::class.java,
                    "meal_db"

                ).build()
            }
            return INSTANCE as MealDatabase
        }
    }
}