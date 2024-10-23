package ru.webarmour.foodapp.domain.usecase

import ru.webarmour.foodapp.domain.MealRepository
import ru.webarmour.foodapp.domain.model.MealItem
import javax.inject.Inject

class DeleteMealFromDbUseCase @Inject constructor(
    private val repository: MealRepository
) {

    suspend operator fun invoke(mealItem: MealItem) = repository.deleteMealFromDb(mealItem)
}