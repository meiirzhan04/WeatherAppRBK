package edu.learn.weatherapprbk.feature.home.presentation

import edu.learn.weatherapprbk.core.setup.BaseWeatherSetup
import edu.learn.weatherapprbk.domain.model.ForecastData
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import kotlin.collections.orEmpty

fun createInitialHomeState(baseWeatherSetup: BaseWeatherSetup): HomeState {
    val cachedWeather = baseWeatherSetup.getCachedWeather()
    val cachedForecast = baseWeatherSetup.getCachedForecastData()
    return HomeState(
        weather = cachedWeather?.mergeDailyTemperatureRange(cachedForecast),
        lastKnownWeather = cachedWeather?.mergeDailyTemperatureRange(cachedForecast),
        weatherDetails = cachedForecast?.details,
        forecast = cachedForecast?.daily.orEmpty(),
        hourlyForecast = cachedForecast?.hourly.orEmpty(),
        isUsingCachedData = cachedWeather != null
    )
}
fun WeatherInfo.mergeDailyTemperatureRange(forecastData: ForecastData?): WeatherInfo {
    val today = forecastData?.daily?.firstOrNull() ?: return this
    return copy(tempMin = today.minTemp.toDouble(), tempMax = today.maxTemp.toDouble())
}
