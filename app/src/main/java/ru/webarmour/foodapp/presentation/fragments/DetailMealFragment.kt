package ru.webarmour.foodapp.presentation.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.webarmour.foodapp.MealApp
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.databinding.FragmentDetailMealBinding
import ru.webarmour.foodapp.domain.model.MealItem
import ru.webarmour.foodapp.presentation.viewmodel.DetailViewModel
import ru.webarmour.foodapp.presentation.viewmodel.DetailViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class DetailMealFragment : Fragment() {


    private var _binding: FragmentDetailMealBinding? = null
    private val binding get() = _binding!!

    private lateinit var mealId: String
    private lateinit var youTubeLink: String
    private lateinit var viewModel: DetailViewModel
    private var currentMeal:MealItem? = null
    @Inject
    lateinit var db: MealDatabase
    @Inject
    lateinit var viewModelFactory: DetailViewModelFactory



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailMealBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        return binding.root

    }

    fun onFavouriteClick(){
        binding.floatingButton.setOnClickListener {
                currentMeal?.let { it1 -> viewModel.insertMeal(it1.copy(isFavourite = true)) }
                Toast.makeText(
                    requireContext(), "${currentMeal!!.strMeal}is added to favourites",
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRandomMealDetail()
        viewModel.getDetailMealById(mealId)
        observeLoading()
        observeDetailMeal()
        onYoutubeImageClick()
        onFavouriteClick()


    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.floatingButton.visibility = View.GONE
                binding.tvArea.visibility = View.GONE
                binding.tvCategory.visibility = View.GONE
                binding.progressIndicator.visibility = View.VISIBLE
            } else {
                binding.floatingButton.visibility = View.VISIBLE
                binding.tvArea.visibility = View.VISIBLE
                binding.tvCategory.visibility = View.VISIBLE
                binding.progressIndicator.visibility = View.GONE
            }
        }
    }

    private fun observeDetailMeal() {
        viewModel.detailMealLiveData.observe(viewLifecycleOwner) {
            currentMeal = it
            updateUi(it)
        }
    }

    private fun updateUi(it: MealItem) {
        Glide.with(this).load(it.strMealThumb).into(binding.imgMealDetail)
        binding.tvMealName.text = it.strMeal
        binding.tvCategory.text = "Category: ${it.strCategory}"
        binding.tvArea.text = "Area: ${it.strArea}"
        binding.tvInstructionsSteps.text = it.strInstructions
        youTubeLink = it.strYoutube.toString()
    }

    private fun getRandomMealDetail() {
        arguments?.let {
            mealId = it.getString(HomeFragment.MEAL_ID)!!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}