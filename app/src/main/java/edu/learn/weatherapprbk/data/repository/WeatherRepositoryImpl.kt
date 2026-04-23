package edu.learn.weatherapprbk.data.repository

import edu.learn.weatherapprbk.BuildConfig
import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.core.repository.BaseRepository
import edu.learn.weatherapprbk.core.repository.mappedApiCall
import edu.learn.weatherapprbk.data.local.dao.WeatherDao
import edu.learn.weatherapprbk.data.remote.api.WeatherApi
import edu.learn.weatherapprbk.data.local.entity.toDomain
import edu.learn.weatherapprbk.data.local.entity.toEntity
import edu.learn.weatherapprbk.data.remote.mapper.ForecastMapper
import edu.learn.weatherapprbk.data.remote.mapper.WeatherMapper
import edu.learn.weatherapprbk.domain.model.ForecastData
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import edu.learn.weatherapprbk.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val mapper: WeatherMapper,
    private val forecastMapper: ForecastMapper,
    private val weatherDao: WeatherDao
) : BaseRepository, WeatherRepository {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): ResultState<WeatherInfo> {
        val result = mappedApiCall(mapper) {
            api.getCurrentWeather(lat = lat, lon = lon, apiKey = BuildConfig.OPEN_WEATHER_API_KEY)
        }
        if (result is ResultState.Success) {
            weatherDao.upsertWeather(result.data.toEntity())
        }
        return result
    }

    override suspend fun getForecast(lat: Double, lon: Double): ResultState<ForecastData> {
        return mappedApiCall(forecastMapper) {
            api.getForecast(lat = lat, lon = lon, apiKey = BuildConfig.OPEN_WEATHER_API_KEY)
        }
    }

    override suspend fun getCachedWeather(): WeatherInfo? = weatherDao.getWeather()?.toDomain()
}
