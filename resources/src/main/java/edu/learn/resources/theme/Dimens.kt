package edu.learn.resources.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class WeatherAppRBKDimensions(
    val zero: Dp = 0.dp,
    val halfHairline: Dp = 0.5.dp,
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
    val mediumMediumLarge: Dp = 22.dp,
    val mediumLarge: Dp = 24.dp,
    val large: Dp = 28.dp,
    val extraLarge: Dp = 32.dp,
    val extraLargeMedium: Dp = 38.dp,
    val xxxLarge: Dp = 44.dp,
    val xxxxLarge: Dp = 48.dp,
    val xxxxxLarge: Dp = 50.dp,
    val xxxxxxLarge: Dp = 56.dp,
    val xxxxxxxLarge: Dp = 70.dp,
    val xxLarge: Dp = 80.dp,
    val xxxXLarge: Dp = 96.dp,
    val detailCardMinHeight: Dp = 160.dp,
    val headerExpandedHeight: Dp = 180.dp,
    val fullRound: Dp = 999.dp,

    val screenHorizontalPadding: Dp = 16.dp,
    val screenVerticalPadding: Dp = 16.dp,

    val statusCardCornerRadius: Dp = 28.dp,
    val glassCardCornerRadius: Dp = 22.dp,
    val cardCornerRadius: Dp = 12.dp,
    val buttonCornerRadius: Dp = 14.dp,
    val textFieldCornerRadius: Dp = 12.dp,

    val topBarTopInset: Dp = 48.dp,
    val headerTopSpacer: Dp = 50.dp,
    val headerCollapseOffset: Dp = 56.dp,
    val floatingButtonSize: Dp = 44.dp,
    val floatingShadowElevation: Dp = 4.dp,
    val cityCardHeight: Dp = 96.dp,
    val forecastRowHeight: Dp = 38.dp,
    val forecastLabelWidth: Dp = 70.dp,
    val temperatureValueWidth: Dp = 32.dp,
    val temperatureBarWidth: Dp = 96.dp,
    val iconSmall: Dp = 14.dp,
    val iconMedium: Dp = 20.dp,
    val iconLarge: Dp = 22.dp
)

val LocalWeatherAppRBKDimensions = staticCompositionLocalOf { WeatherAppRBKDimensions() }
