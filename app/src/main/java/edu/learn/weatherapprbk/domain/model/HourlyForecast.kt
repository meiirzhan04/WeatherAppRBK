package edu.learn.weatherapprbk.domain.model

data class HourlyForecast(
    val timeLabel: String,
    val temperature: Int,
    val condition: String,
    val iconCode: String = ""
)
