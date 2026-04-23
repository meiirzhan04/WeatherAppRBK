package edu.learn.resources.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class WeatherAppRBKDimensions(
    val hairline: Dp = 1.dp,
    val extraExtraSmall: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val smallExtra: Dp = 6.dp,
    val small: Dp = 8.dp,
    val smallMedium: Dp = 10.dp,
    val extraMedium: Dp = 12.dp,
    val mediumExtra: Dp = 14.dp,
    val medium: Dp = 16.dp,
    val mediumSmall: Dp = 18.dp,
    val mediumMedium: Dp = 20.dp,
    val mediumLarge: Dp = 24.dp,
    val large: Dp = 28.dp,
    val extraLarge: Dp = 32.dp,
    val xxLarge: Dp = 80.dp,

    val screenHorizontalPadding: Dp = 16.dp,
    val screenVerticalPadding: Dp = 16.dp,

    val cardCornerRadius: Dp = 16.dp,
    val buttonCornerRadius: Dp = 14.dp,
    val textFieldCornerRadius: Dp = 12.dp
)

val LocalWeatherAppRBKDimensions = staticCompositionLocalOf { WeatherAppRBKDimensions() }
