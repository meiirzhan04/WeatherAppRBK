package edu.learn.weatherapprbk.feature.home.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import edu.learn.weatherapprbk.R
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun feelsLikeMessage(
    feelsLike: Double,
    actualTemperature: Double
): String {
    val stringId = when {
        feelsLike - actualTemperature >= 2.0 -> R.string.details_feels_warmer
        actualTemperature - feelsLike >= 2.0 -> R.string.details_feels_cooler
        else -> R.string.details_feels_same
    }
    return stringResource(stringId)
}

@Composable
fun averageMaxMessage(
    todayValue: Double,
    averageMaxValue: Double
): String {
    val diff = todayValue - averageMaxValue
    val stringId = when {
        diff >= 2.0 -> R.string.details_avg_max_above
        diff <= -2.0 -> R.string.details_avg_max_below
        else -> R.string.details_avg_max_near
    }
    return stringResource(stringId)
}

@Composable
fun averageMaxDiffText(
    todayValue: Double,
    averageMaxValue: Double
): String {
    val diff = abs(todayValue - averageMaxValue).roundToInt()
    return stringResource(R.string.details_diff_prefix, "$diff\u00B0")
}

@Composable
fun uvLevelLabel(uvIndex: Double): String {
    val stringId = when {
        uvIndex < 3 -> R.string.details_uv_low
        uvIndex < 6 -> R.string.details_uv_moderate
        uvIndex < 8 -> R.string.details_uv_high
        uvIndex < 11 -> R.string.details_uv_very_high
        else -> R.string.details_uv_extreme
    }
    return stringResource(stringId)
}

fun formatTemperature(value: Double): String = "${value.roundToInt()}\u00B0"
fun metersPerSecondToKmPerHour(value: Double): Int = (value * 3.6).roundToInt()
fun windDirectionLabel(degrees: Int): String {
    val normalizedDegrees = ((degrees % 360) + 360) % 360
    val sector = ((normalizedDegrees / 45.0).roundToInt()) % 8
    val direction = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")[sector]
    return "$degrees\u00B0 $direction"
}
