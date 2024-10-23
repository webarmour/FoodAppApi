package ru.webarmour.foodapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.webarmour.foodapp.R
import ru.webarmour.foodapp.data.room.MapperDbToDomain
import ru.webarmour.foodapp.databinding.FragmentFavouritesBinding
import ru.webarmour.foodapp.presentation.MealsAdapter
import ru.webarmour.foodapp.presentation.fragments.HomeFragment.Companion.MEAL_ID
import ru.webarmour.foodapp.presentation.viewmodel.MainViewModel

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var mapperDbToDomain: MapperDbToDomain
    private lateinit var adapter:MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        mapperDbToDomain = MapperDbToDomain()
        adapter = MealsAdapter()
    }
    fun initRcView(){
        binding.rvFavourites.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.rvFavourites.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observeFavouritesMeals()
        onCategoryItemClick()
        swipeDeleteItem()
    }

    private fun swipeDeleteItem() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentItem = adapter.currentList[position]
                viewModel.deleteMeal(adapter.currentList[position])
                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(currentItem)
                    }
                ).show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
    }

    private fun onCategoryItemClick() {
        adapter.onItemClick = {
            val bundle = Bundle().apply {
                putString(MEAL_ID, it.idMeal)
            }
            findNavController().navigate(R.id.action_favouritesFragment_to_detailMealFragment, bundle)

        }
    }

    private fun observeFavouritesMeals() {
        viewModel.favouritesMealLiveData.observe(viewLifecycleOwner) {
            var mealList = mapperDbToDomain.mapListDbToDomain(it).toMutableList()
            adapter.submitList(mealList)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}