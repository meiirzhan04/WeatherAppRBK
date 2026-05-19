package edu.learn.weatherapprbk.domain.usecase

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.ForecastData
import edu.learn.weatherapprbk.domain.repository.WeatherRepository

class GetForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): ResultState<ForecastData> {
        return repository.getForecast(lat, lon)
    }
}
