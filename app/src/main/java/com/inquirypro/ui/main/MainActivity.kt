package com.inquirypro.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.inquirypro.R
import com.inquirypro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavMenu.itemIconTintList = null


        binding.bottomNavMenu.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.menuHome->{
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.menuCategory -> {
                    navController.navigate(R.id.categoryFragment)
                    true
                }
                R.id.menuStory -> {
                    navController.navigate(R.id.questionStoriesFragment)
                    true
                }
                R.id.menuUser -> {
                    navController.navigate(R.id.userFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}