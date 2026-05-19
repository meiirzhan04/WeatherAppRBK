package edu.learn.weatherapprbk.feature.home.presentation.components

import androidx.annotation.DrawableRes
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.core.resolver.OpenWeatherIconResolver
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import java.util.Calendar
import java.util.Locale

object WeatherVisualResolver {
    @DrawableRes
    fun resolveBackground(
        weather: WeatherInfo,
        currentTimeMillis: Long = System.currentTimeMillis()
    ): Int {
        val rainy = isRainy(weather.condition.lowercase(Locale.ROOT))
        val timeSlot = resolveTimeSlot(currentTimeMillis)

        return when {
            rainy && timeSlot == TimeSlot.MORNING -> R.drawable.sunny_running
            rainy && timeSlot == TimeSlot.DAY -> R.drawable.running
            rainy && timeSlot == TimeSlot.EVENING -> R.drawable.dark_running
            rainy && timeSlot == TimeSlot.NIGHT -> R.drawable.verydark_running

            !rainy && timeSlot == TimeSlot.MORNING -> R.drawable.sunny
            !rainy && timeSlot == TimeSlot.DAY -> R.drawable.day
            !rainy && timeSlot == TimeSlot.EVENING -> R.drawable.little_dark
            !rainy && timeSlot == TimeSlot.NIGHT -> R.drawable.dark
            else -> R.drawable.day
        }
    }
    fun resolveConditionText(description: String, fallbackText: String): String {
        if (description.trim().isBlank()) return fallbackText
        return description.trim().replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
        }
    }
    fun resolveHourlyWeatherIcon(condition: String, iconCode: String = ""): Int {
        return OpenWeatherIconResolver.resolve(iconCode = iconCode, fallbackCondition = condition)
    }
    private fun isRainy(condition: String): Boolean {
        val value = condition.lowercase(Locale.ROOT)
        return value.contains("rain") || value.contains("drizzle") || value.contains("thunderstorm")
    }
    private fun resolveTimeSlot(currentTimeMillis: Long): TimeSlot {
        val calendar = Calendar.getInstance().apply { timeInMillis = currentTimeMillis }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 6..11 -> TimeSlot.MORNING
            in 12..17 -> TimeSlot.DAY
            in 18..23 -> TimeSlot.EVENING
            else -> TimeSlot.NIGHT
        }
    }
    private enum class TimeSlot {
        MORNING, DAY, EVENING, NIGHT
    }
}