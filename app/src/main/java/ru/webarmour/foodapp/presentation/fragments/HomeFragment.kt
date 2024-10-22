package ru.webarmour.foodapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.webarmour.foodapp.R
import ru.webarmour.foodapp.databinding.FragmentHomeBinding
import ru.webarmour.foodapp.domain.model.MealItem
import ru.webarmour.foodapp.presentation.CategoriesAdapter
import ru.webarmour.foodapp.presentation.PopularMealAdapter
import ru.webarmour.foodapp.presentation.bottomsheet.MealBottomSheetFragment
import ru.webarmour.foodapp.presentation.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var randomMeal: MealItem? = null
    private lateinit var popularMealAdapter: PopularMealAdapter
    private lateinit var categoryMealsAdapter: CategoriesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        refreshDataOnLifecycle()
        observingRandomMeal()
        observingListMeals()
        observeCategories()
        onCategoryItemClick()
        onCategoryClick()
        onPopularItemLongClick()
        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularMealAdapter.onLongItemClick = {
            val mealBottomSheetFragment = MealBottomSheetFragment
                .newInstance(it.idMeal)
            Log.d("MEAL_TAG", it.strMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")

        }
    }

    private fun refreshDataOnLifecycle() {
        lifecycleScope.launch {
            viewModel.getListRandomMeal()
            viewModel.getCategoriesOfMeals()
        }
    }


    private fun observeCategories() {
        viewModel.categoryItem.observe(viewLifecycleOwner) {
            categoryMealsAdapter.submitList(it)
        }
    }

    private fun onCategoryClick() {
        categoryMealsAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putString(MEAL_ID, it.strCategory)
            }
            findNavController().navigate(R.id.action_homeFragment_to_categoryMealFragment, bundle)

        }
    }

    private fun onCategoryItemClick() {
        popularMealAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putString(MEAL_ID, it.idMeal)
            }
            findNavController().navigate(R.id.action_homeFragment_to_detailMealFragment, bundle)

        }
    }

    private fun initRcView() {
        popularMealAdapter = PopularMealAdapter()
        binding.rcViewPopularMeals.adapter = popularMealAdapter
        categoryMealsAdapter = CategoriesAdapter()
        binding.rcViewCategories.adapter = categoryMealsAdapter
        binding.rcViewCategories.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
    }

    private fun observingListMeals() {
        viewModel.randomListMealLiveData.observe(viewLifecycleOwner) {
            popularMealAdapter.submitList(it)
        }
    }

    private fun observingRandomMeal() {
        viewModel.randomMealLiveData.observe(viewLifecycleOwner) {
            Glide.with(this@HomeFragment)
                .load(it.strMealThumb)
                .into(binding.imgRandomMeal)
            binding.tvDayDish.text = "Random meal: ${it.strMeal}"
            this.randomMeal = it
            onRandomMealClick()
        }
    }

    private fun onRandomMealClick() {
        val bundle = Bundle().apply {
            putString(MEAL_ID, randomMeal!!.idMeal)
        }

        binding.randomMealCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailMealFragment, bundle)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val MEAL_ID = "meal_id"
        const val MEAL_NAME = "meal_name"
        const val MEAL_THUMB = "meal_thumb"
    }

}