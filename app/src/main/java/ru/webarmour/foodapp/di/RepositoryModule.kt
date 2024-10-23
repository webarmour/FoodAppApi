package ru.webarmour.foodapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.webarmour.foodapp.data.MealRepositoryImpl
import ru.webarmour.foodapp.domain.MealRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @Binds
    fun bindMealRepository(repositoryImpl: MealRepositoryImpl): MealRepository
}