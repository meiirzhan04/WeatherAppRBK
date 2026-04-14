package edu.learn.resources.preferences

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.text.util.LocalePreferences

val LocalPreferences = staticCompositionLocalOf<AppPreferences> {
    error("LocalPreferences not provided")
}

data class AppPreferences(
    val localePreferences: LocalePreferences,
    val themePreferences: ThemePreferences
)

