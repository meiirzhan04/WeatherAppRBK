package edu.learn.weatherapprbk.domain.repository

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.ForecastData
import edu.learn.weatherapprbk.domain.model.ForecastDay
import edu.learn.weatherapprbk.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): ResultState<WeatherInfo>
    suspend fun getForecast(lat: Double, lon: Double): ResultState<ForecastData>
    suspend fun getCachedWeather(): WeatherInfo?
}
