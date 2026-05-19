package edu.learn.resources.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember


@Composable
fun WeatherAppRBKTheme(
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val typography = defaultSanaTypography()
    val colors = if (isDark) DarkColors else LightColors
    val dimensions = WeatherAppRBKDimensions()

    WeatherAppRBKThemeImpl(
        colors = colors,
        content = content,
        typography = typography,
        dimensions = dimensions
    )
}

@Composable
fun WeatherAppRBKThemeImpl(
    typography: WeatherAppRBKTypography,
    colors: WeatherAppRBKColor,
    dimensions: WeatherAppRBKDimensions,
    content: @Composable () -> Unit,
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    CompositionLocalProvider(
        LocalWeatherAppRBKColors provides rememberedColors,
        LocalWeatherAppRBKTypography provides typography,
        LocalWeatherAppRBKDimensions provides dimensions
    ) { ProvideTextStyle(value = typography.weight400Size12LineHeight16, content) }
}

object WeatherAppRBKTheme {
    val colors: WeatherAppRBKColor
        @Composable
        @ReadOnlyComposable
        get() = LocalWeatherAppRBKColors.current

    val typography: WeatherAppRBKTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalWeatherAppRBKTypography.current

    val dimensions: WeatherAppRBKDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalWeatherAppRBKDimensions.current
}