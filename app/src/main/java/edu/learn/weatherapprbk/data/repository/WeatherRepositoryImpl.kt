package edu.learn.weatherapprbk.data.repository

import edu.learn.weatherapprbk.BuildConfig
import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.core.repository.BaseRepository
import edu.learn.weatherapprbk.core.repository.mappedApiCall
import edu.learn.weatherapprbk.data.remote.api.WeatherApi
import edu.learn.weatherapprbk.data.remote.mapper.WeatherMapper
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import edu.learn.weatherapprbk.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val mapper: WeatherMapper
) : BaseRepository, WeatherRepository {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): ResultState<WeatherInfo> {
        return mappedApiCall(mapper) {
            api.getCurrentWeather(lat = lat, lon = lon, apiKey = BuildConfig.OPEN_WEATHER_API_KEY)
        }
    }
}