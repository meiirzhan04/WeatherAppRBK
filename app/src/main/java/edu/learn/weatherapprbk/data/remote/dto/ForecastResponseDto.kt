package edu.learn.weatherapprbk.data.remote.dto

data class ForecastResponseDto(
    val timezone: String = "",
    val timezone_offset: Int = 0,
    val current: ForecastCurrentDto = ForecastCurrentDto(),
    val hourly: List<ForecastHourlyDto> = emptyList(),
    val daily: List<ForecastDailyDto> = emptyList()
)
