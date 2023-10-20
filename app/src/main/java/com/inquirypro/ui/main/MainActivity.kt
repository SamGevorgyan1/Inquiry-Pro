package com.inquirypro.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inquirypro.R
import com.inquirypro.databinding.ActivityMainBinding
import com.inquirypro.ui.main.callbacks.ButtonClickListener
import com.inquirypro.ui.main.callbacks.OpenQuestionFragmentListener
import com.inquirypro.ui.viewmodel.SharedViewModel
import com.inquirypro.util.Constants
import com.inquirypro.util.NavigationUtils

class MainActivity : AppCompatActivity(), OpenQuestionFragmentListener, ButtonClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bottomNavigationView = binding.bottomNavMenu

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavMenu.itemIconTintList = null
        binding.bottomNavMenu.itemRippleColor = null
        binding.bottomNavMenu.itemActiveIndicatorColor = null


        binding.bottomNavMenu.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHome -> navController.navigate(R.id.homeFragment)
                R.id.menuCategory -> navController.navigate(R.id.categoryFragment)
                R.id.menuHistory -> navController.navigate(R.id.historyFragment)
                R.id.menuArticle -> navController.navigate(R.id.articleFragment)
                R.id.menuProfile -> navController.navigate(R.id.userFragment)
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun homeFragmentToPart(fragment: Int, categoryId: Int) {

        val bundle = Bundle().apply { putInt(Constants.CATEGORY_ID, categoryId) }

        findNavController(fragment).navigate(
            R.id.action_homeFragment_to_partFragment,
            bundle,
            NavigationUtils.defaultScaleAnimation()
        )
    }

    override fun openQuestion() {
        binding.bottomNavMenu.visibility = View.GONE
    }

    override fun closeQuestion() {
        binding.bottomNavMenu.visibility = View.VISIBLE
    }

    override fun articleButton() {
        binding.bottomNavMenu.selectedItemId = R.id.menuArticle
    }

    override fun categoryButton() {
        binding.bottomNavMenu.selectedItemId = R.id.menuCategory
    }
}