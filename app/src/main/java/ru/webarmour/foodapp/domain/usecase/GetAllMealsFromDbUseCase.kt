package ru.webarmour.foodapp.domain.usecase

import ru.webarmour.foodapp.domain.MealRepository
import ru.webarmour.foodapp.domain.model.MealItem
import javax.inject.Inject

class GetAllMealsFromDbUseCase @Inject constructor(
    private val repository: MealRepository
) {

    operator fun invoke() = repository.getAllMeals()
}