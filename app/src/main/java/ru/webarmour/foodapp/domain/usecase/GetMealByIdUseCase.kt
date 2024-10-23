package ru.webarmour.foodapp.domain.usecase

import ru.webarmour.foodapp.domain.MealRepository
import javax.inject.Inject

class GetMealByIdUseCase@Inject constructor(
    private val mealRepository: MealRepository
) {


    suspend operator fun invoke(id:String) = mealRepository.getMealById(id)
}