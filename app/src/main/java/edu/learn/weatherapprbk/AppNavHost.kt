package edu.learn.weatherapprbk

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import edu.learn.weatherapprbk.feature.home.HomeScreenDestination
import edu.learn.weatherapprbk.feature.home.homeGraph

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.Graph
    ) {
        homeGraph(navController)
    }
}