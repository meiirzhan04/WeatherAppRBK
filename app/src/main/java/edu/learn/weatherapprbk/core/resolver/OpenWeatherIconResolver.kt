package edu.learn.weatherapprbk.core.resolver

import androidx.annotation.DrawableRes
import edu.learn.weatherapprbk.R

object OpenWeatherIconResolver {
    @DrawableRes
    fun resolve(iconCode: String, fallbackCondition: String = ""): Int {
        val code = iconCode.lowercase()
        val isNight = code.endsWith("n")
        return when {
            code in setOf("09d", "09n", "10d", "10n", "11d", "11n") -> R.drawable.ic_rain
            code == "01d" -> R.drawable.ic_sun
            isNight -> R.drawable.ic_moon_2
            code in setOf("02d", "03d", "04d", "50d", "13d") -> R.drawable.ic_cloud
            else -> fallback(fallbackCondition, iconCode)
        }
    }

    @DrawableRes
    private fun fallback(condition: String, iconCode: String): Int {
        val value = condition.lowercase()
        return when {
            value.contains("rain") || value.contains("drizzle") || value.contains("thunderstorm") -> R.drawable.ic_rain
            value.contains("cloud") || value.contains("mist") || value.contains("fog") || value.contains("snow") -> R.drawable.ic_cloud
            iconCode.endsWith("n", ignoreCase = true) -> R.drawable.ic_moon_2
            else -> R.drawable.ic_sun
        }
    }
}
