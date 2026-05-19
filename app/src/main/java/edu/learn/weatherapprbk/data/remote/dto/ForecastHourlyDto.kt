package edu.learn.weatherapprbk.data.remote.dto

data class ForecastHourlyDto(
    val dt: Long = 0L,
    val temp: Double = 0.0,
    val weather: List<WeatherDto> = emptyList()
)
