package edu.learn.weatherapprbk.domain.model

data class WeatherInfo(
    val cityName: String,
    val lat: Double,
    val lon: Double,
    val temperature: Double,
    val tempMin: Double,
    val tempMax: Double,
    val feelsLike: Double,
    val description: String,
    val condition: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val timezoneOffsetSeconds: Int,
    val sunriseEpochSeconds: Long,
    val sunsetEpochSeconds: Long,
    val updatedAtMillis: Long,
    val tempAvg: Double
)
