package edu.learn.weatherapprbk.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import edu.learn.weatherapprbk.feature.detail.presentation.DetailScreen
import edu.learn.weatherapprbk.feature.home.presentation.HomeScreen
import kotlinx.serialization.Serializable
@Serializable
sealed interface HomeScreenDestination {
    @Serializable
    data object Graph : HomeScreenDestination
    @Serializable
    data object Main : HomeScreenDestination
    @Serializable
    data object Detail : HomeScreenDestination
}


fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<HomeScreenDestination.Graph>(startDestination = HomeScreenDestination.Main) {
        composable<HomeScreenDestination.Main> {
            HomeScreen(
                onWeatherDetailsClick = {
                    navController.navigate(HomeScreenDestination.Detail)
                }
            )
        }
        composable<HomeScreenDestination.Detail> {
            DetailScreen()
        }
    }
}
