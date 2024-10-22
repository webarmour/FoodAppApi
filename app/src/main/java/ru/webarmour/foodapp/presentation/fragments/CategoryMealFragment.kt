package ru.webarmour.foodapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ru.webarmour.foodapp.R
import ru.webarmour.foodapp.databinding.FragmentCategoryMealBinding
import ru.webarmour.foodapp.databinding.FragmentDetailMealBinding
import ru.webarmour.foodapp.presentation.CategoriesAdapter
import ru.webarmour.foodapp.presentation.MealsByCategoryAdapter
import ru.webarmour.foodapp.presentation.PopularMealAdapter
import ru.webarmour.foodapp.presentation.viewmodel.CategoryMealViewModel
import ru.webarmour.foodapp.presentation.viewmodel.DetailViewModel

class CategoryMealFragment : Fragment() {

    private var _binding: FragmentCategoryMealBinding? = null
    private val binding get() = _binding!!

    private var mealCategory: String? = null
    private val viewModel: CategoryMealViewModel by viewModels()
    private lateinit var mealByCategoryAdapter: MealsByCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCategoryMealBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getCategoryMealName()
        initRcView()
//        observeCategories()
//        if(mealCategory != null){
//            viewModel.getMealsByCategory(mealCategory!!)
//        }
       viewModel.getMealsByCategory("Chicken")
        viewModel.categoryMeals.observe(viewLifecycleOwner){
            mealByCategoryAdapter.submitList(it)
        }
    }
//
//    fun observeCategories(){
//        viewModel.categoryMeals.observe(viewLifecycleOwner){
////            popularMealAdapter.submitList(it)
//        }
//    }
//    private fun getCategoryMealName() {
//        arguments?.let {
//            mealCategory = it.getString(HomeFragment.MEAL_ID)!!
//        }
//    }
//
    fun initRcView(){
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