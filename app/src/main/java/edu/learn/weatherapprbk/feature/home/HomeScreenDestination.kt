package edu.learn.weatherapprbk.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailCityCardUi
import edu.learn.weatherapprbk.feature.detail.presentation.DetailScreen
import edu.learn.weatherapprbk.feature.home.presentation.HomeScreen
import kotlinx.serialization.Serializable
@Serializable
sealed interface HomeScreenDestination {
    @Serializable
    data object Graph : HomeScreenDestination
    @Serializable
    data class Main(
        val cityId: String? = null,
        val latitude: Double? = null,
        val longitude: Double? = null
    ) : HomeScreenDestination
    @Serializable
    data object Detail : HomeScreenDestination
}


fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<HomeScreenDestination.Graph>(startDestination = HomeScreenDestination.Main()) {
        composable<HomeScreenDestination.Main> { backStackEntry ->
            val route = backStackEntry.toRoute<HomeScreenDestination.Main>()
            HomeScreen(
                selectedCityId = route.cityId,
                selectedLatitude = route.latitude,
                selectedLongitude = route.longitude,
                onWeatherDetailsClick = {
                    navController.navigate(HomeScreenDestination.Detail)
                }
            )
        }
        composable<HomeScreenDestination.Detail> {
            DetailScreen(
                onOpenCity = { city ->
                    navController.navigate(city.toMainDestination())
                }
            )
        }
    }
}

private fun DetailCityCardUi.toMainDestination(): HomeScreenDestination.Main =
    HomeScreenDestination.Main(
        cityId = cityId,
        latitude = latitude,
        longitude = longitude
    )
