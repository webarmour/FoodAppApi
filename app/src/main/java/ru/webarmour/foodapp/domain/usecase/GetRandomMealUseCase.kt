package ru.webarmour.foodapp.domain.usecase

import ru.webarmour.foodapp.domain.MealRepository

class GetRandomMealUseCase(private val mealRepository: MealRepository) {


    operator fun invoke() = mealRepository.getRandomMeal()
}