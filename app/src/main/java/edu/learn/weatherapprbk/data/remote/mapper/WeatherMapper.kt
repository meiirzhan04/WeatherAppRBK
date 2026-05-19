package edu.learn.weatherapprbk.data.remote.mapper

import edu.learn.weatherapprbk.core.mapper.BaseMapper
import edu.learn.weatherapprbk.data.remote.dto.WeatherResponseDto
import edu.learn.weatherapprbk.domain.model.WeatherInfo

class WeatherMapper : BaseMapper<WeatherResponseDto, WeatherInfo> {
    override fun map(source: WeatherResponseDto): WeatherInfo {
        return WeatherInfo(
            cityName = source.name,
            temperature = source.main.temp,
            tempMin = source.main.temp_min,
            tempMax = source.main.temp_max,
            feelsLike = source.main.feels_like,
            humidity = source.main.humidity,
            description = source.weather.firstOrNull()?.description.orEmpty(),
            condition = source.weather.firstOrNull()?.main.orEmpty(),
            pressure = source.main.pressure,
            windSpeed = source.wind.speed,
            lat = source.coord.lat,
            lon = source.coord.lon,
            timezoneOffsetSeconds = source.timezone,
            sunriseEpochSeconds = source.sys.sunrise,
            sunsetEpochSeconds = source.sys.sunset,
            updatedAtMillis = System.currentTimeMillis(),
            tempAvg = (source.main.temp_min + source.main.temp_max) / 2
        )
    }
}
