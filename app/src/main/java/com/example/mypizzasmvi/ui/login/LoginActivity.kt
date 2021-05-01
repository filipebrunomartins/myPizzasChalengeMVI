package com.example.mypizzasmvi.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mypizzasmvi.R
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class LoginActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadKoinModules(module)
        val bundle = savedInstanceState ?: intent.extras
        setupNavigationController(bundle)
    }

    private fun setupNavigationController(bundle: Bundle?) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_login) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.login_nav_graph, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(module)
    }

    companion object {
        val module = module(override = true) {
            viewModel { LoginViewModel(get()) }
        }
    }
}