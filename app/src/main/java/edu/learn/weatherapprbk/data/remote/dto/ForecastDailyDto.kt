package edu.learn.weatherapprbk.data.remote.dto

data class ForecastDailyDto(
    val dt: Long = 0L,
    val temp: ForecastDailyTempDto = ForecastDailyTempDto(),
    val weather: List<WeatherDto> = emptyList()
)
