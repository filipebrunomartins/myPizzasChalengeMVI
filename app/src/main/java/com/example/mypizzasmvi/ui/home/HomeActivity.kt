package com.example.mypizzasmvi.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mypizzasmvi.R
import com.example.mypizzasmvi.databinding.ActivityHomeBinding
import com.example.mypizzasmvi.ui.home.pizzaList.PizzaListViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(module)
        val bundle = savedInstanceState ?: intent.extras
        setupNavigationController(bundle)
    }

    private fun setupNavigationController(bundle: Bundle?) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_home) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.home_nav_graph, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(module)
    }

    companion object {
        val module = module(override = true) {
            viewModel { PizzaListViewModel(get()) }
        }
    }
}