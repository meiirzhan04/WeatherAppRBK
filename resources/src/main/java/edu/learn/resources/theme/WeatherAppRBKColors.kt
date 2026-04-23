package edu.learn.resources.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LightColors = WeatherAppRBKColor(
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
    tabBarContainer = TabBarContainerLight,
    tabBarItemSelected = TabBarItemSelectedLight,
    tabBarItemUnselected = TabBarItemUnselectedLight,
    tabBarIconSelected = TabBarIconSelectedLight,
    tabBarIconUnselected = TabBarIconUnselectedLight,
    whiteTransparent = WhiteTransparent
)

internal val DarkColors = WeatherAppRBKColor(
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
    tabBarContainer = TabBarContainerDark,
    tabBarItemSelected = TabBarItemSelectedDark,
    tabBarItemUnselected = TabBarItemUnselectedDark,
    tabBarIconSelected = TabBarIconSelectedDark,
    tabBarIconUnselected = TabBarIconUnselectedDark,
    whiteTransparent = WhiteTransparent
)

@Immutable
class WeatherAppRBKColor(
    primary: Color,
    secondary: Color,
    background: Color,
    surface: Color,
    card: Color,
    textPrimary: Color,
    textSecondary: Color,
    onPrimary: Color,
    onSecondary: Color,
    divider: Color,
    border: Color,
    success: Color,
    error: Color,
    whiteTransparent: Color,
    tabBarContainer: Color,
    tabBarItemSelected: Color,
    tabBarItemUnselected: Color,
    tabBarIconSelected: Color,
    tabBarIconUnselected: Color
) {
    var primary by mutableStateOf(primary)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var background by mutableStateOf(background)
        private set

    var surface by mutableStateOf(surface)
        private set

    var card by mutableStateOf(card)
        private set

    var textPrimary by mutableStateOf(textPrimary)
        private set

    var textSecondary by mutableStateOf(textSecondary)
        private set

    var onPrimary by mutableStateOf(onPrimary)
        private set

    var onSecondary by mutableStateOf(onSecondary)
        private set

    var divider by mutableStateOf(divider)
        private set

    var border by mutableStateOf(border)
        private set

    var success by mutableStateOf(success)
        private set

    var error by mutableStateOf(error)
        private set

    var tabBarContainer by mutableStateOf(tabBarContainer)
        private set

    var tabBarItemSelected by mutableStateOf(tabBarItemSelected)
        private set

    var tabBarItemUnselected by mutableStateOf(tabBarItemUnselected)
        private set

    var tabBarIconSelected by mutableStateOf(tabBarIconSelected)
        private set

    var tabBarIconUnselected by mutableStateOf(tabBarIconUnselected)
        private set

    var whiteTransparent by mutableStateOf(whiteTransparent)
        private set


    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        background: Color = this.background,
        surface: Color = this.surface,
        card: Color = this.card,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        onPrimary: Color = this.onPrimary,
        onSecondary: Color = this.onSecondary,
        divider: Color = this.divider,
        border: Color = this.border,
        success: Color = this.success,
        error: Color = this.error,
        tabBarContainer: Color = this.tabBarContainer,
        tabBarItemSelected: Color = this.tabBarItemSelected,
        tabBarItemUnselected: Color = this.tabBarItemUnselected,
        tabBarIconSelected: Color = this.tabBarIconSelected,
        tabBarIconUnselected: Color = this.tabBarIconUnselected,
        whiteTransparent: Color = this.whiteTransparent
    ) = WeatherAppRBKColor(
        primary = primary,
        secondary = secondary,
        background = background,
        surface = surface,
        card = card,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        divider = divider,
        border = border,
        success = success,
        error = error,
        tabBarContainer = tabBarContainer,
        tabBarItemSelected = tabBarItemSelected,
        tabBarItemUnselected = tabBarItemUnselected,
        tabBarIconSelected = tabBarIconSelected,
        tabBarIconUnselected = tabBarIconUnselected,
        whiteTransparent = whiteTransparent
    )

    fun updateColorsFrom(other: WeatherAppRBKColor) {
        primary = other.primary
        secondary = other.secondary
        background = other.background
        surface = other.surface
        card = other.card
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        divider = other.divider
        border = other.border
        success = other.success
        error = other.error
        tabBarContainer = other.tabBarContainer
        tabBarItemSelected = other.tabBarItemSelected
        tabBarItemUnselected = other.tabBarItemUnselected
        tabBarIconSelected = other.tabBarIconSelected
        tabBarIconUnselected = other.tabBarIconUnselected
        whiteTransparent = other.whiteTransparent
    }
}

val LocalWeatherAppRBKColors = staticCompositionLocalOf { LightColors }