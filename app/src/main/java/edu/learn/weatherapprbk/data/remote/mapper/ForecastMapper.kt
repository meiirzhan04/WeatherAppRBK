package edu.learn.weatherapprbk.data.remote.mapper

import edu.learn.resources.datetime.WeatherDateTimeFormatter
import edu.learn.weatherapprbk.core.mapper.BaseMapper
import edu.learn.weatherapprbk.data.remote.dto.ForecastResponseDto
import edu.learn.weatherapprbk.domain.model.ForecastData
import edu.learn.weatherapprbk.domain.model.ForecastDay
import edu.learn.weatherapprbk.domain.model.HourlyForecast
import edu.learn.weatherapprbk.domain.model.WeatherDetails
import kotlin.math.roundToInt

class ForecastMapper : BaseMapper<ForecastResponseDto, ForecastData> {
    override fun map(source: ForecastResponseDto): ForecastData {
        val details = WeatherDetails(
            uvIndex = source.current.uvi,
            pressure = source.current.pressure,
            humidity = source.current.humidity,
            dewPoint = source.current.dew_point,
            feelsLike = source.current.feels_like,
            windSpeed = source.current.wind_speed,
            windGust = source.current.wind_gust,
            windDirectionDegrees = source.current.wind_deg,
            sunriseEpochSeconds = source.current.sunrise,
            sunsetEpochSeconds = source.current.sunset
        )

        val daily = source.daily
            .mapIndexed { index, items ->
                ForecastDay(
                    dateLabel = WeatherDateTimeFormatter.dayLabel(
                        timestampSeconds = items.dt,
                        timezoneOffsetSeconds = source.timezone_offset
                    ),
                    isToday = index == 0,
                    minTemp = items.temp.min.roundToInt(),
                    maxTemp = items.temp.max.roundToInt(),
                    condition = items.weather.firstOrNull()?.main.orEmpty(),
                    iconCode = items.weather.firstOrNull()?.icon.orEmpty()
                )
            }
            .take(8)

        val hourly = source.hourly
            .take(24)
            .map { item ->
                HourlyForecast(
                    timeLabel = WeatherDateTimeFormatter.hourLabel(
                        timestampSeconds = item.dt,
                        timezoneOffsetSeconds = source.timezone_offset
                    ),
                    temperature = item.temp.roundToInt(),
                    condition = item.weather.firstOrNull()?.main.orEmpty(),
                    iconCode = item.weather.firstOrNull()?.icon.orEmpty()
                )
            }
        return ForecastData(details = details, daily = daily, hourly = hourly)
    }
}
