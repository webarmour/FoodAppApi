package ru.webarmour.foodapp.domain.usecase

import ru.webarmour.foodapp.domain.MealRepository
import javax.inject.Inject

class GetMealsByCategoryUseCase@Inject constructor(
    private val mealRepository: MealRepository,
) {


    suspend operator fun invoke(
        category: String,
    ) = mealRepository.getMealsByCategory(category)
}