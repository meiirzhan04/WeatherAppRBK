package edu.learn.weatherapprbk.data.remote.api

import edu.learn.weatherapprbk.BuildConfig
import edu.learn.weatherapprbk.data.remote.dto.CityDto
import edu.learn.weatherapprbk.data.remote.dto.ForecastResponseDto
import edu.learn.weatherapprbk.data.remote.dto.WeatherResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherServiceKtor(
    private val client: HttpClient
) : WeatherService {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String,
        units: String,
        lang: String
    ): WeatherResponseDto {
        return client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("appid", apiKey)
            parameter("units", units)
            parameter("lang", lang)
        }.body<WeatherResponseDto>()
    }

    override suspend fun getForecast(
        lat: Double,
        lon: Double,
        apiKey: String,
        units: String,
        lang: String,
        exclude: String
    ): ForecastResponseDto {
        return client.get("https://api.openweathermap.org/data/3.0/onecall") {
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("appid", apiKey)
            parameter("units", units)
            parameter("lang", lang)
            parameter("exclude", exclude)
        }.body<ForecastResponseDto>()
    }

    override suspend fun getListOfCities(cityName: String): List<CityDto> {
        return client.get("https://api.openweathermap.org/geo/1.0/direct") {
            parameter("q", cityName)
            parameter("limit", 3)
            parameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
        }.body<List<CityDto>>()
    }
}
