package edu.learn.weatherapprbk.data.remote.dto

data class WeatherResponseDto(
    val coord: CoordDto,
    val weather: List<WeatherDto>,
    val main: MainDto,
    val wind: WindDto,
    val name: String
)
