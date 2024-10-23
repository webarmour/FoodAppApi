package ru.webarmour.foodapp.presentation.fragments

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.webarmour.foodapp.R
import ru.webarmour.foodapp.data.room.MealDatabase
import ru.webarmour.foodapp.databinding.ActivityMainBinding
import ru.webarmour.foodapp.presentation.viewmodel.MainViewModel
import ru.webarmour.foodapp.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mealDatabase: MealDatabase

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    val viewModel: MainViewModel by viewModels {
        mainViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navController.popBackStack()) {
                    finish()
                }
            }
        })
    }
}