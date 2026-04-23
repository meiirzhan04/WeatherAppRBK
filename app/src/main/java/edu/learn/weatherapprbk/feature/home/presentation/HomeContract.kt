package edu.learn.weatherapprbk.feature.home.presentation

import androidx.compose.runtime.Immutable
import edu.learn.weatherapprbk.domain.model.ForecastDay
import edu.learn.weatherapprbk.domain.model.HourlyForecast
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import edu.learn.weatherapprbk.domain.model.WeatherDetails


@Immutable
data class HomeState(
    val isSystemStateKnown: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val weather: WeatherInfo? = null,
    val lastKnownWeather: WeatherInfo? = null,
    val weatherDetails: WeatherDetails? = null,
    val forecast: List<ForecastDay> = emptyList(),
    val hourlyForecast: List<HourlyForecast> = emptyList(),
    val error: HomeError? = null,
    val hasLocationPermission: Boolean = false,
    val isLocationEnabled: Boolean = false,
    val isUsingCachedData: Boolean = false
)
sealed interface HomeIntent {
    data class Initialize(
        val hasLocationPermission: Boolean,
        val isLocationEnabled: Boolean
    ) : HomeIntent
    data class PermissionResult(
        val granted: Boolean,
        val isLocationEnabled: Boolean
    ) : HomeIntent
    data class SystemStatusChanged(
        val hasLocationPermission: Boolean,
        val isLocationEnabled: Boolean
    ) : HomeIntent
    data object Refresh : HomeIntent
    data object Retry : HomeIntent
}
sealed interface HomeEffect {

}

sealed interface HomeError {
    data object PermissionDenied : HomeError
    data object LocationDisabled : HomeError
    data object NoInternet : HomeError
    data class Server(val code: Int?) : HomeError
    data class Unknown(val message: String) : HomeError
}
