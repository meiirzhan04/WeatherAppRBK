package edu.learn.weatherapprbk.domain.usecase

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import edu.learn.weatherapprbk.domain.repository.WeatherRepository

class GetCurrentWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): ResultState<WeatherInfo> {
        return repository.getCurrentWeather(lat, lon)
    }
}