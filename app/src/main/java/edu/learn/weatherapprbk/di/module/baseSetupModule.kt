package edu.learn.weatherapprbk.di.module

import android.content.Context
import edu.learn.weatherapprbk.core.setup.BaseWeatherSetup
import edu.learn.weatherapprbk.data.local.WeatherLocalStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val WEATHER_BASE_PREFS = "weather_base_prefs"

val baseSetupModule = module {
    single { androidContext().getSharedPreferences(WEATHER_BASE_PREFS, Context.MODE_PRIVATE) }
    single { WeatherLocalStorage(get()) }
    single { BaseWeatherSetup(context = androidContext(), localStorage = get()) }
}
