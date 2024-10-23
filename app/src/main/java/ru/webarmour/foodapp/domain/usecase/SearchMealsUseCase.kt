package ru.webarmour.foodapp.domain.usecase

import ru.webarmour.foodapp.domain.MealRepository
import javax.inject.Inject

class SearchMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {


    suspend operator fun invoke(
        searchQuery: String
    ) = mealRepository.searchMeals(searchQuery)
}