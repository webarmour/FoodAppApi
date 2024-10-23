package ru.webarmour.foodapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.webarmour.foodapp.databinding.FragmentSearchBinding
import ru.webarmour.foodapp.presentation.SearchAdapter
import ru.webarmour.foodapp.presentation.viewmodel.MainViewModel

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observeSearch()
        searchClickListener()
        searchTextChangedListener()
    }

    private fun searchClickListener() {
        binding.imgSearchArrow.setOnClickListener {
            searchMeals()
        }
    }

    private fun searchTextChangedListener() {
        var searchJob: Job? = null
        binding.etSearchBox.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500L)
                viewModel.searchMeals(searchQuery.toString())
            }
        }
    }

    private fun searchMeals() {
        val searchQuery = binding.etSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            Log.d("SearchFragment", "searchMeals() is being called with query: $searchQuery")
            viewModel.searchMeals(searchQuery)
        }

    }


    private fun observeSearch() {
        viewModel.searchedMealLiveData.observe(viewLifecycleOwner) {
            Log.d("MEAL_TAG", "$it")
            adapter.submitList(it)
        }
    }


    private fun initRcView() {
        adapter = SearchAdapter()
        binding.rvSearchMeals.adapter = adapter
        binding.rvSearchMeals.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}