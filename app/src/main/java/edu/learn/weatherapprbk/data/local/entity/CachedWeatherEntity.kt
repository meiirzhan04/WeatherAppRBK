package edu.learn.weatherapprbk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.learn.weatherapprbk.domain.model.WeatherInfo

@Entity(tableName = "cached_weather")
data class CachedWeatherEntity(
    @PrimaryKey
    val id: Int = DEFAULT_ID,
    val cityName: String,
    val lat: Double,
    val lon: Double,
    val temperature: Double,
    val tempMin: Double,
    val tempMax: Double,
    val feelsLike: Double,
    val description: String,
    val condition: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val timezoneOffsetSeconds: Int,
    val sunriseEpochSeconds: Long,
    val sunsetEpochSeconds: Long,
    val updatedAtMillis: Long,
    val tempAvg: Double
) {
    companion object {
        const val DEFAULT_ID = 0
    }
}

fun CachedWeatherEntity.toDomain(): WeatherInfo = WeatherInfo(
    cityName = cityName,
    lat = lat,
    lon = lon,
    temperature = temperature,
    tempMin = tempMin,
    tempMax = tempMax,
    feelsLike = feelsLike,
    description = description,
    condition = condition,
    humidity = humidity,
    pressure = pressure,
    windSpeed = windSpeed,
    timezoneOffsetSeconds = timezoneOffsetSeconds,
    sunriseEpochSeconds = sunriseEpochSeconds,
    sunsetEpochSeconds = sunsetEpochSeconds,
    updatedAtMillis = updatedAtMillis,
    tempAvg = tempAvg
)

fun WeatherInfo.toEntity(): CachedWeatherEntity = CachedWeatherEntity(
    cityName = cityName,
    lat = lat,
    lon = lon,
    temperature = temperature,
    tempMin = tempMin,
    tempMax = tempMax,
    feelsLike = feelsLike,
    description = description,
    condition = condition,
    humidity = humidity,
    pressure = pressure,
    windSpeed = windSpeed,
    timezoneOffsetSeconds = timezoneOffsetSeconds,
    sunriseEpochSeconds = sunriseEpochSeconds,
    sunsetEpochSeconds = sunsetEpochSeconds,
    updatedAtMillis = updatedAtMillis,
    tempAvg = tempAvg
)
