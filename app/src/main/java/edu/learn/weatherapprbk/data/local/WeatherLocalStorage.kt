package edu.learn.weatherapprbk.data.local

import android.content.SharedPreferences
import edu.learn.weatherapprbk.domain.model.UserLocation
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import androidx.core.content.edit

class WeatherLocalStorage(
    private val sharedPreferences: SharedPreferences
) {
    fun saveLastLocation(location: UserLocation) {
        sharedPreferences.edit {
            putDouble(KEY_LOCATION_LAT, location.latitude)
                .putDouble(KEY_LOCATION_LON, location.longitude)
        }
    }

    fun getSavedLocation(): UserLocation? {
        val latitude = sharedPreferences.getDoubleOrNull(KEY_LOCATION_LAT) ?: return null
        val longitude = sharedPreferences.getDoubleOrNull(KEY_LOCATION_LON) ?: return null
        return UserLocation(latitude = latitude, longitude = longitude)
    }

    fun saveLastWeather(
        weather: WeatherInfo,
        updatedAtMillis: Long = System.currentTimeMillis()
    ) {
        sharedPreferences.edit {
            putString(KEY_WEATHER_CITY, weather.cityName)
                .putDouble(KEY_WEATHER_LAT, weather.lat)
                .putDouble(KEY_WEATHER_LON, weather.lon)
                .putDouble(KEY_WEATHER_TEMP, weather.temperature)
                .putDouble(KEY_WEATHER_FEELS_LIKE, weather.feelsLike)
                .putString(KEY_WEATHER_DESCRIPTION, weather.description)
                .putInt(KEY_WEATHER_HUMIDITY, weather.humidity)
                .putDouble(KEY_WEATHER_WIND_SPEED, weather.windSpeed)
                .putLong(KEY_WEATHER_UPDATED_AT, updatedAtMillis)
        }
    }

    fun getCachedWeather(): WeatherInfo? {
        val cityName = sharedPreferences.getString(KEY_WEATHER_CITY, null) ?: return null
        val lat = sharedPreferences.getDoubleOrNull(KEY_WEATHER_LAT) ?: return null
        val lon = sharedPreferences.getDoubleOrNull(KEY_WEATHER_LON) ?: return null
        val temperature = sharedPreferences.getDoubleOrNull(KEY_WEATHER_TEMP) ?: return null
        val feelsLike = sharedPreferences.getDoubleOrNull(KEY_WEATHER_FEELS_LIKE) ?: return null
        val description = sharedPreferences.getString(KEY_WEATHER_DESCRIPTION, null) ?: return null
        val humidity = sharedPreferences.getInt(KEY_WEATHER_HUMIDITY, Int.MIN_VALUE)
        val windSpeed = sharedPreferences.getDoubleOrNull(KEY_WEATHER_WIND_SPEED) ?: return null

        if (humidity == Int.MIN_VALUE) return null

        return WeatherInfo(
            cityName = cityName,
            lat = lat,
            lon = lon,
            temperature = temperature,
            feelsLike = feelsLike,
            description = description,
            humidity = humidity,
            windSpeed = windSpeed
        )
    }

    fun getLastUpdateTime(): Long? =
        sharedPreferences.takeIf { it.contains(KEY_WEATHER_UPDATED_AT) }
            ?.getLong(KEY_WEATHER_UPDATED_AT, 0L)

    fun clearWeatherCache() {
        sharedPreferences.edit {
            remove(KEY_WEATHER_CITY)
                .remove(KEY_WEATHER_LAT)
                .remove(KEY_WEATHER_LON)
                .remove(KEY_WEATHER_TEMP)
                .remove(KEY_WEATHER_FEELS_LIKE)
                .remove(KEY_WEATHER_DESCRIPTION)
                .remove(KEY_WEATHER_HUMIDITY)
                .remove(KEY_WEATHER_WIND_SPEED)
                .remove(KEY_WEATHER_UPDATED_AT)
        }
    }

    private fun SharedPreferences.getDoubleOrNull(key: String): Double? {
        if (!contains(key)) return null
        return Double.fromBits(getLong(key, 0L))
    }

    private fun SharedPreferences.Editor.putDouble(key: String, value: Double): SharedPreferences.Editor =
        putLong(key, value.toBits())

    private companion object {
        const val KEY_LOCATION_LAT = "key_location_lat"
        const val KEY_LOCATION_LON = "key_location_lon"
        const val KEY_WEATHER_CITY = "key_weather_city"
        const val KEY_WEATHER_LAT = "key_weather_lat"
        const val KEY_WEATHER_LON = "key_weather_lon"
        const val KEY_WEATHER_TEMP = "key_weather_temp"
        const val KEY_WEATHER_FEELS_LIKE = "key_weather_feels_like"
        const val KEY_WEATHER_DESCRIPTION = "key_weather_description"
        const val KEY_WEATHER_HUMIDITY = "key_weather_humidity"
        const val KEY_WEATHER_WIND_SPEED = "key_weather_wind_speed"
        const val KEY_WEATHER_UPDATED_AT = "key_weather_updated_at"
    }
}
