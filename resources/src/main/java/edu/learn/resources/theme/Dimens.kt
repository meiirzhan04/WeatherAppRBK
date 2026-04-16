package edu.learn.resources.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class WeatherAppRBKDimensions(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,

    val screenHorizontalPadding: Dp = 16.dp,
    val screenVerticalPadding: Dp = 16.dp,

    val cardCornerRadius: Dp = 16.dp,
    val buttonCornerRadius: Dp = 14.dp,
    val textFieldCornerRadius: Dp = 12.dp
)

val LocalWeatherAppRBKDimensions = staticCompositionLocalOf { WeatherAppRBKDimensions() }