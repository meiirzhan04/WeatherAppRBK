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
        val hourly = buildHourlyWithSunEvents(source)
        return ForecastData(details = details, daily = daily, hourly = hourly)
    }

    private fun buildHourlyWithSunEvents(source: ForecastResponseDto): List<HourlyForecast> {
        val nowTs = source.current.dt
        val todaySunrise = source.current.sunrise
        val todaySunset = source.current.sunset

        val sunriseTs = if (todaySunrise < nowTs) todaySunrise + 86400L else todaySunrise
        val sunsetTs = if (todaySunset < nowTs) todaySunset + 86400L else todaySunset

        val hourlyItems = source.hourly.take(24)
        val result = mutableListOf<HourlyForecast>()
        val firstDt = hourlyItems.firstOrNull()?.dt ?: 0L

        if (sunriseTs in (nowTs + 1)..firstDt) {
            result.add(HourlyForecast(
                timeLabel = WeatherDateTimeFormatter.timeLabel(sunriseTs, source.timezone_offset),
                temperature = 0,
                condition = SUNRISE,
                iconCode = "",
                isSunrise = true
            ))
        }
        hourlyItems.forEachIndexed { index, item ->
            if (sunriseTs > nowTs && index > 0 && hourlyItems[index - 1].dt < sunriseTs && item.dt >= sunriseTs) {
                result.add(HourlyForecast(
                    timeLabel = WeatherDateTimeFormatter.timeLabel(sunriseTs, source.timezone_offset),
                    temperature = 0,
                    condition = SUNRISE,
                    iconCode = "",
                    isSunrise = true
                ))
            }
            if (sunsetTs > nowTs && index > 0 && hourlyItems[index - 1].dt < sunsetTs && item.dt >= sunsetTs) {
                result.add(HourlyForecast(
                    timeLabel = WeatherDateTimeFormatter.timeLabel(sunsetTs, source.timezone_offset),
                    temperature = 0,
                    condition = SUNSET,
                    iconCode = "",
                    isSunset = true
                ))
            }
            result.add(HourlyForecast(
                timeLabel = WeatherDateTimeFormatter.hourLabel(item.dt, source.timezone_offset),
                temperature = item.temp.roundToInt(),
                condition = item.weather.firstOrNull()?.main.orEmpty(),
                iconCode = item.weather.firstOrNull()?.icon.orEmpty(),
                isNow = index == 0
            ))
        }
        return result
    }
    companion object {
        private const val SUNSET = "sunset"
        private const val SUNRISE = "sunrise"
    }
}