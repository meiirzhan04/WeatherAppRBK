package edu.learn.weatherapprbk.data.remote.dto

data class ForecastCurrentDto(
    val dt: Long = 0L,
    val sunrise: Long = 0L,
    val sunset: Long = 0L,
    val temp: Double = 0.0,
    val feels_like: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val dew_point: Double = 0.0,
    val uvi: Double = 0.0,
    val wind_speed: Double = 0.0,
    val wind_deg: Int = 0,
    val wind_gust: Double = 0.0,
    val weather: List<WeatherDto> = emptyList()
)
