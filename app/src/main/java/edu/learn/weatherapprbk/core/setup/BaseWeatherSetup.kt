package edu.learn.weatherapprbk.core.setup

import android.content.Context
import edu.learn.weatherapprbk.core.extensions.DEFAULT_LOCATION_THRESHOLD_METERS
import edu.learn.weatherapprbk.core.extensions.hasLocationPermission
import edu.learn.weatherapprbk.core.extensions.isInternetAvailable
import edu.learn.weatherapprbk.core.extensions.isLocationEnabled
import edu.learn.weatherapprbk.core.extensions.isSameLocation
import edu.learn.weatherapprbk.data.local.WeatherLocalStorage
import edu.learn.weatherapprbk.domain.model.ForecastData
import edu.learn.weatherapprbk.domain.model.UserLocation
import edu.learn.weatherapprbk.domain.model.WeatherInfo

class BaseWeatherSetup(
    private val context: Context,
    private val localStorage: WeatherLocalStorage
) {
    fun hasLocationPermission(): Boolean = context.hasLocationPermission()

    fun isLocationEnabled(): Boolean = context.isLocationEnabled()

    fun isInternetAvailable(): Boolean = context.isInternetAvailable()

    fun saveLastLocation(location: UserLocation) {
        localStorage.saveLastLocation(location)
    }

    fun getSavedLocation(): UserLocation? = localStorage.getSavedLocation()

    fun isSameLocation(
        oldLocation: UserLocation,
        newLocation: UserLocation,
        thresholdMeters: Float = DEFAULT_LOCATION_THRESHOLD_METERS
    ): Boolean = oldLocation.isSameLocation(newLocation, thresholdMeters)

    fun saveLastWeather(weather: WeatherInfo) {
        localStorage.saveLastWeather(weather)
    }

    fun getCachedWeather(): WeatherInfo? = localStorage.getCachedWeather()

    fun saveLastForecastData(forecastData: ForecastData) {
        localStorage.saveLastForecastData(forecastData)
    }

    fun getCachedForecastData(): ForecastData? = localStorage.getCachedForecastData()

    fun getLastUpdateTime(): Long? = localStorage.getLastUpdateTime()

    fun isCacheFresh(
        lastUpdateTime: Long? = localStorage.getLastUpdateTime(),
        maxAgeMinutes: Int = 15
    ): Boolean {
        val updateTime = lastUpdateTime ?: return false
        val maxAgeMillis = maxAgeMinutes.coerceAtLeast(1) * 60_000L
        return System.currentTimeMillis() - updateTime <= maxAgeMillis
    }

    fun shouldRefreshWeather(maxAgeMinutes: Int = 15): Boolean =
        !isCacheFresh(maxAgeMinutes = maxAgeMinutes)

    fun clearWeatherCache() {
        localStorage.clearWeatherCache()
    }
}
