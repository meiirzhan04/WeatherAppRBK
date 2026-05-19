package edu.learn.weatherapprbk.data.remote.api

import edu.learn.weatherapprbk.data.remote.dto.CityDto
import edu.learn.weatherapprbk.data.remote.dto.ForecastResponseDto
import edu.learn.weatherapprbk.data.remote.dto.WeatherResponseDto

interface WeatherService {
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String,
        units: String = "metric",
        lang: String = "ru"
    ): WeatherResponseDto

    suspend fun getForecast(
        lat: Double,
        lon: Double,
        apiKey: String,
        units: String = "metric",
        lang: String = "ru",
        exclude: String = "minutely,alerts"
    ): ForecastResponseDto

    suspend fun getListOfCities(cityName: String): List<CityDto>
}
