package edu.learn.weatherapprbk.data.remote.mapper

import edu.learn.weatherapprbk.core.mapper.BaseMapper
import edu.learn.weatherapprbk.data.remote.dto.WeatherResponseDto
import edu.learn.weatherapprbk.domain.model.WeatherInfo

class WeatherMapper : BaseMapper<WeatherResponseDto, WeatherInfo> {
    override fun map(source: WeatherResponseDto): WeatherInfo {
        val weatherItem = source.weather.firstOrNull()
        return WeatherInfo(
            cityName = source.name,
            temperature = source.main.temp,
            feelsLike = source.main.feels_like,
            humidity = source.main.humidity,
            description = weatherItem?.description.orEmpty(),
            windSpeed = source.wind.speed,
            lat = source.coord.lat,
            lon = source.coord.lon
        )
    }
}