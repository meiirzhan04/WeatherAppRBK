package edu.learn.weatherapprbk.domain.model

data class ForecastDay(
    val dateLabel: String,
    val isToday: Boolean,
    val minTemp: Int,
    val maxTemp: Int,
    val condition: String,
    val iconCode: String = ""
)
