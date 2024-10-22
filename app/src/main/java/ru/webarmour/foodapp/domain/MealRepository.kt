package ru.webarmour.foodapp.domain

import ru.webarmour.foodapp.domain.model.MealItem

interface MealRepository {

    fun getRandomMeal():MealItem
}