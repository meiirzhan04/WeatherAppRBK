package edu.learn.weatherapprbk.domain.model

data class WeatherInfo(
    val cityName: String,
    val lat: Double,
    val lon: Double,
    val temperature: Double,
    val feelsLike: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double
)