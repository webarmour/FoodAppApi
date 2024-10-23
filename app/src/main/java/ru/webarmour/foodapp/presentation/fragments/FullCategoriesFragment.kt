package ru.webarmour.foodapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.webarmour.foodapp.R
import ru.webarmour.foodapp.databinding.FragmentFullCategoriesBinding
import ru.webarmour.foodapp.presentation.CategoriesAdapter
import ru.webarmour.foodapp.presentation.fragments.HomeFragment.Companion.MEAL_ID
import ru.webarmour.foodapp.presentation.viewmodel.MainViewModel

@AndroidEntryPoint
class FullCategoriesFragment : Fragment() {

    private var _binding: FragmentFullCategoriesBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var adapter: CategoriesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFullCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observeCategories()
        onCategoryItemClick()
    }

    private fun onCategoryItemClick() {
        adapter.onItemClick = {
            val bundle = Bundle().apply {
                putString(MEAL_ID, it.strCategory)
            }
            findNavController().navigate(
                R.id.action_fullCategoriesFragment_to_categoryMealFragment,
                bundle
            )

        }
    }

    private fun observeCategories() {
        viewModel.categoryItem.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initRcView() {
        adapter = CategoriesAdapter()
        binding.rvCategories.layoutManager = GridLayoutManager(
            context,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.rvCategories.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}