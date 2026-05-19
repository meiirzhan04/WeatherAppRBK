package edu.learn.weatherapprbk.data.remote.dto

data class CityDto(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)