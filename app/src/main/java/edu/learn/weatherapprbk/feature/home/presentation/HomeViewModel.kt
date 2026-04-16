package edu.learn.weatherapprbk.feature.home.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import edu.learn.weatherapprbk.core.architecture.BaseViewModel
import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.usecase.GetCurrentLocationUseCase
import edu.learn.weatherapprbk.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : BaseViewModel<HomeIntent, HomeState, HomeEffect>(initialState = HomeState()) {
    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadWeatherByCurrentLocation -> loadWeatherByCurrentLocation()
            HomeIntent.Retry -> loadWeatherByCurrentLocation()
        }
    }
    private fun loadWeatherByCurrentLocation() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            when (val locationResult = getCurrentLocationUseCase()) {
                is ResultState.Error -> {
                    Log.e("LOCATION_ERROR", locationResult.message)
                    setState { copy(isLoading = false, error = locationResult.message) }
                }
                is ResultState.Success -> {
                    Log.d("LOCATION_RESULT", "lat = ${locationResult.data.latitude}, lon = ${locationResult.data.longitude}")
                    setState { copy(locationText = "lat = ${locationResult.data.latitude}, lon = ${locationResult.data.longitude}") }
                    when (val weatherResult = getCurrentWeatherUseCase(lat = locationResult.data.latitude, lon = locationResult.data.longitude)) {
                        is ResultState.Error -> {
                            Log.e("WEATHER_ERROR", weatherResult.message)
                            setState { copy(isLoading = false, error = weatherResult.message) }
                        }
                        is ResultState.Success -> {
                            Log.d("WEATHER_API", weatherResult.toString())
                            setState { copy(isLoading = false, weather = weatherResult.data, error = null) }
                        }
                        else -> {}
                    }
                }
                else -> {}
            }
        }
    }
}