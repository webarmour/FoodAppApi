package ru.webarmour.foodapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.webarmour.foodapp.R
import ru.webarmour.foodapp.databinding.FragmentCategoryMealBinding
import ru.webarmour.foodapp.presentation.MealsByCategoryAdapter
import ru.webarmour.foodapp.presentation.fragments.HomeFragment.Companion.MEAL_ID
import ru.webarmour.foodapp.presentation.viewmodel.CategoryMealViewModel

@AndroidEntryPoint
class CategoryMealFragment : Fragment() {

    private var _binding: FragmentCategoryMealBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryMealViewModel by viewModels()
    private lateinit var mealByCategoryAdapter: MealsByCategoryAdapter
    private var mealCategory: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCategoryMealBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCategoryMealName()
        initRcView()
        if (mealCategory != null) {
            viewModel.getMealsByCategory(mealCategory!!)
        }
        viewModel.categoryMeals.observe(viewLifecycleOwner) {
            mealByCategoryAdapter.submitList(it)
            val currentListCount = mealByCategoryAdapter.currentList.size
            binding.tvCategoryCount.text =
                getString(R.string.meals_count, mealCategory, "$currentListCount")
        }
        onCategoryItemClick()
    }

    private fun onCategoryItemClick() {
        mealByCategoryAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putString(MEAL_ID, it.idMeal)
            }
            findNavController().navigate(R.id.action_categoryMealFragment_to_detailMealFragment, bundle)
        }
    }


    private fun getCategoryMealName() {
        arguments?.let {
            mealCategory = it.getString(HomeFragment.MEAL_ID)!!
        }
    }

    fun initRcView() {
        binding.rvMealsCategory.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        mealByCategoryAdapter = MealsByCategoryAdapter()
        binding.rvMealsCategory.adapter = mealByCategoryAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}