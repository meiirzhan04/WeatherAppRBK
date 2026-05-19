package edu.learn.weatherapprbk.domain.usecase

import edu.learn.weatherapprbk.domain.model.WeatherInfo
import edu.learn.weatherapprbk.domain.repository.WeatherRepository

class GetCachedWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): WeatherInfo? = repository.getCachedWeather()
}
