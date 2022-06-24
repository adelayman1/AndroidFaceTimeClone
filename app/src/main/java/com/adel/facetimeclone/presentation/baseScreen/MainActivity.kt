package com.adel.facetimeclone.presentation.baseScreen


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.adel.facetimeclone.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FaceTimeClone)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment1) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.startDestination =
                if (viewModel.isLoggedIn()) {
                    R.id.homeFragment
                } else {
                    R.id.loginFragment
                }
        navController.graph = navGraph
    }

}