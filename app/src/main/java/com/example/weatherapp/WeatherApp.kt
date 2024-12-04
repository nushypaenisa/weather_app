package com.example.weatherapp

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.pages.DetailsScreen
import com.example.weatherapp.ui.pages.HomeScreen
import com.example.weatherapp.viewModel.DetailsViewModel
import com.example.weatherapp.viewModel.WeatherViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun WeatherApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val homeViewModel: WeatherViewModel = hiltViewModel()
            HomeScreen(navController, homeViewModel)
        }
        composable("details/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: "Unknown Date"
            val detailViewModel: DetailsViewModel = hiltViewModel()

            DetailsScreen(navController,  detailViewModel, date)
        }
    }
}
