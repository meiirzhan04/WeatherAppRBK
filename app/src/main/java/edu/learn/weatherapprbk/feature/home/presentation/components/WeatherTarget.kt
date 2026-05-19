package edu.learn.weatherapprbk.feature.home.presentation.components

import androidx.compose.runtime.Immutable
import edu.learn.weatherapprbk.domain.model.UserLocation

@Immutable
sealed interface WeatherTarget {
    data object Current : WeatherTarget
    data class City(val id: String, val location: UserLocation) : WeatherTarget
}
