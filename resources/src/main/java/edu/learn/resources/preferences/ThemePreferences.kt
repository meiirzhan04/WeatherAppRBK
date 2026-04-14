package edu.learn.resources.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import edu.learn.resources.components.ThemeMode
import kotlinx.coroutines.CoroutineScope

class ThemePreferences(
    dataStore: PrefsDataStore,
    scope: CoroutineScope
) : BasePreferences<String>(dataStore, scope) {

    override val key: Preferences.Key<String> = stringPreferencesKey("theme_mode")
    override val initialValue: String = ThemeMode.SYSTEM.name

    suspend fun setTheme(mode: ThemeMode) {
        save(mode.name)
    }

    fun getThemeMode(raw: String?): ThemeMode {
        val safe = raw ?: ThemeMode.SYSTEM.name
        return runCatching { ThemeMode.valueOf(safe) }.getOrDefault(ThemeMode.SYSTEM)
    }
}