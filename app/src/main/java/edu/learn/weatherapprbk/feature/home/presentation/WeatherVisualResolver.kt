package edu.learn.weatherapprbk.feature.home.presentation

import androidx.annotation.DrawableRes
import edu.learn.weatherapprbk.core.resolver.OpenWeatherIconResolver
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import java.util.Locale

object WeatherVisualResolver {
    @DrawableRes
    fun resolveBackground(weather: WeatherInfo, currentTimeMillis: Long = System.currentTimeMillis()): Int {
        val condition = weather.condition.lowercase(Locale.ROOT)
        val isDaylight = isDaylight(weather, currentTimeMillis)
        return when {
            condition.containsAny("rain", "drizzle", "thunderstorm") ->
                if (isDaylight) R.drawable.running else R.drawable.verydark_running
            condition.containsAny("cloud", "mist", "fog", "snow") ->
                if (isDaylight) R.drawable.day else R.drawable.dark
            condition.containsAny("clear", "sun") ->
                if (isDaylight) R.drawable.sunny else R.drawable.little_dark
            else -> if (isDaylight) R.drawable.day else R.drawable.dark
        }
    }

    fun resolveConditionText(description: String, fallbackText: String): String {
        val value = description.trim()
        if (value.isBlank()) return fallbackText
        return value.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
        }
    }
    fun resolveHourlyWeatherIcon(condition: String, iconCode: String = ""): Int {
        return OpenWeatherIconResolver.resolve(
            iconCode = iconCode,
            fallbackCondition = condition
        )
    }
    fun isDaylight(
        weather: WeatherInfo,
        currentTimeMillis: Long = System.currentTimeMillis()
    ): Boolean {
        val sunrise = weather.sunriseEpochSeconds
        val sunset = weather.sunsetEpochSeconds
        val currentTimeSeconds = currentTimeMillis / 1000L
        return currentTimeSeconds in sunrise until sunset
    }
    private fun String.containsAny(vararg values: String): Boolean {
        return values.any(::contains)
    }
}
