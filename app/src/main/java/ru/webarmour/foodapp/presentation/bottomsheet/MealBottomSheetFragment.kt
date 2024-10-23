package ru.webarmour.foodapp.presentation.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.webarmour.foodapp.R
import ru.webarmour.foodapp.databinding.FragmentMealBottomSheetBinding
import ru.webarmour.foodapp.presentation.fragments.HomeFragment
import ru.webarmour.foodapp.presentation.fragments.MainActivity
import ru.webarmour.foodapp.presentation.viewmodel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MEAL_ID = "param1"

@AndroidEntryPoint
class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private var _binding: FragmentMealBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var actionExecuted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMealBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let { viewModel.getMealById(it) }
        observeMealById()
        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        if (!actionExecuted) {
            actionExecuted = true
            binding.bottomSheet.setOnClickListener {

                val bundle = Bundle().apply {
                    putString(HomeFragment.MEAL_ID, mealId)
                    Log.d("MEAL_TAG", "$mealId")
                }
                findNavController()
                    .navigate(R.id.action_homeFragment_to_detailMealFragment, bundle,
                        navOptions {
                            popUpTo(R.id.homeFragment) {
                                inclusive = false
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    )


            }
        }
    }

    private fun observeMealById() {
        viewModel.mealByIdLiveData.observe(viewLifecycleOwner) {
            Glide.with(this).load(it?.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetLocation.text = it?.strArea
            binding.tvBottomSheetCategory.text = it?.strCategory
            binding.tvMealName.text = it?.strMeal
        }
    }

    override fun onPause() {
        super.onPause()
        actionExecuted = false
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}