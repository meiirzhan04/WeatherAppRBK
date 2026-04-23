package edu.learn.weatherapprbk.domain.model

data class WeatherDetails(
    val uvIndex: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val feelsLike: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windDirectionDegrees: Int,
    val sunriseEpochSeconds: Long,
    val sunsetEpochSeconds: Long
)
