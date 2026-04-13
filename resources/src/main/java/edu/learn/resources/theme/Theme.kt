package edu.learn.resources.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class WeatherAppRBKColors(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val card: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val divider: Color,
    val border: Color,
    val success: Color,
    val error: Color,
    val tabBarContainer: Color,
    val tabBarItemSelected: Color,
    val tabBarItemUnselected: Color,
    val tabBarIconSelected: Color,
    val tabBarIconUnselected: Color
)

private val LightAppColors = WeatherAppRBKColors(
    primary = PrimaryBlue,
    secondary = AccentTurquoise,
    background = BackgroundLight,
    surface = SurfaceLight,
    card = CardLight,
    textPrimary = TextPrimaryLight,
    textSecondary = TextSecondaryLight,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    divider = DividerLight,
    border = BorderLight,
    success = SuccessColor,
    error = ErrorColor,
    tabBarContainer = TabBarContainer,
    tabBarItemSelected = TabBarItemSelected,
    tabBarItemUnselected = TabBarItemUnselected,
    tabBarIconSelected = TabBarIconSelected,
    tabBarIconUnselected = TabBarIconUnselected
)

private val DarkAppColors = WeatherAppRBKColors(
    primary = PrimaryBlue,
    secondary = AccentTurquoise,
    background = BackgroundDark,
    surface = SurfaceDark,
    card = CardDark,
    textPrimary = TextPrimaryDark,
    textSecondary = TextSecondaryDark,
    onPrimary = OnPrimaryDark,
    onSecondary = OnSecondaryDark,
    divider = DividerDark,
    border = BorderDark,
    success = SuccessColor,
    error = ErrorColor,
    tabBarContainer = TabBarContainer,
    tabBarItemSelected = TabBarItemSelected,
    tabBarItemUnselected = TabBarItemUnselected,
    tabBarIconSelected = TabBarIconSelected,
    tabBarIconUnselected = TabBarIconUnselected
)

private val LightMaterialColors = lightColorScheme(
    primary = PrimaryBlue,
    secondary = AccentTurquoise,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    error = ErrorColor
)

private val DarkMaterialColors = darkColorScheme(
    primary = PrimaryBlue,
    secondary = AccentTurquoise,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnPrimaryDark,
    onSecondary = OnSecondaryDark,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    error = ErrorColor
)

val LocalColors = staticCompositionLocalOf { LightAppColors }

@Composable
fun WeatherAppRBKTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkAppColors else LightAppColors
    val materialColors = if (darkTheme) DarkMaterialColors else LightMaterialColors
    val typography = WeatherAppRBKTypography()
    val dimensions = WeatherAppRBKDimensions()

    CompositionLocalProvider(
        LocalColors provides appColors,
        LocalWeatherAppRBKTypography provides typography,
        LocalSpacing provides dimensions
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = MaterialWeatherAppRBKTypography,
            shapes = WeatherAppRBKShapes,
            content = content
        )
    }
}

object WeatherAppRBKTheme {
    val colors: WeatherAppRBKColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: WeatherAppRBKTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalWeatherAppRBKTypography.current

    val dimensions: WeatherAppRBKDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current
}