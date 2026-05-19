package edu.learn.weatherapprbk.domain.model

data class ForecastData(
    val details: WeatherDetails,
    val daily: List<ForecastDay>,
    val hourly: List<HourlyForecast>
)
