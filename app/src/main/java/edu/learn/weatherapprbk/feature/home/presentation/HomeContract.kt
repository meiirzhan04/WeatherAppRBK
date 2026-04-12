package edu.learn.weatherapprbk.feature.home.presentation

import androidx.compose.runtime.Immutable
import edu.learn.weatherapprbk.domain.model.WeatherInfo


@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val weather: WeatherInfo? = null,
    val error: String? = null,
    val locationText: String? = null
)
sealed interface HomeIntent {
    data object LoadWeatherByCurrentLocation : HomeIntent
    data object Retry : HomeIntent
}
sealed interface HomeEffect {
    data class ShowToast(val message: String) : HomeEffect
}