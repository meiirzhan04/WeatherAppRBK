package edu.learn.weatherapprbk.data.remote.mapper

import edu.learn.weatherapprbk.core.mapper.BaseMapper
import edu.learn.weatherapprbk.data.remote.dto.WeatherResponseDto
import edu.learn.weatherapprbk.domain.model.WeatherInfo

class WeatherMapper : BaseMapper<WeatherResponseDto, WeatherInfo> {
    override fun map(input: WeatherResponseDto): WeatherInfo {
        val weatherItem = input.weather.firstOrNull()
        return WeatherInfo(
            cityName = input.name,
            temperature = input.main.temp,
            feelsLike = input.main.feels_like,
            humidity = input.main.humidity,
            description = weatherItem?.description.orEmpty(),
            windSpeed = input.wind.speed,
            lat = input.coord.lat,
            lon = input.coord.lon
        )
    }
}