package edu.learn.weatherapprbk.feature.home.presentation

import androidx.lifecycle.viewModelScope
import edu.learn.weatherapprbk.core.architecture.BaseViewModel
import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.core.repository.NetworkException
import edu.learn.weatherapprbk.core.setup.BaseWeatherSetup
import edu.learn.weatherapprbk.domain.model.ForecastData
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import edu.learn.weatherapprbk.domain.usecase.GetCachedWeatherUseCase
import edu.learn.weatherapprbk.domain.usecase.GetCurrentLocationUseCase
import edu.learn.weatherapprbk.domain.usecase.GetCurrentWeatherUseCase
import edu.learn.weatherapprbk.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.launch

private fun createInitialHomeState(baseWeatherSetup: BaseWeatherSetup): HomeState {
    val cachedWeather = baseWeatherSetup.getCachedWeather()
    val cachedForecast = baseWeatherSetup.getCachedForecastData()
    return HomeState(
        weather = cachedWeather?.mergeDailyTemperatureRange(cachedForecast),
        lastKnownWeather = cachedWeather?.mergeDailyTemperatureRange(cachedForecast),
        weatherDetails = cachedForecast?.details,
        forecast = cachedForecast?.daily.orEmpty(),
        hourlyForecast = cachedForecast?.hourly.orEmpty(),
        isUsingCachedData = cachedWeather != null
    )
}

private fun WeatherInfo.mergeDailyTemperatureRange(forecastData: ForecastData?): WeatherInfo {
    val today = forecastData?.daily?.firstOrNull() ?: return this
    return copy(tempMin = today.minTemp.toDouble(), tempMax = today.maxTemp.toDouble())
}

class HomeViewModel(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getCachedWeatherUseCase: GetCachedWeatherUseCase,
    private val baseWeatherSetup: BaseWeatherSetup
) : BaseViewModel<HomeIntent, HomeState, HomeEffect>(
    initialState = createInitialHomeState(baseWeatherSetup)
) {

    init {
        if (state.value.weather == null) {
            viewModelScope.launch {
                getCachedWeatherUseCase()?.let { cachedWeather ->
                    val cachedForecast = baseWeatherSetup.getCachedForecastData()
                    setState {
                        copy(
                            weather = cachedWeather.mergeDailyTemperatureRange(cachedForecast),
                            lastKnownWeather = cachedWeather.mergeDailyTemperatureRange(cachedForecast),
                            weatherDetails = cachedForecast?.details,
                            forecast = cachedForecast?.daily.orEmpty(),
                            hourlyForecast = cachedForecast?.hourly.orEmpty(),
                            isUsingCachedData = true
                        )
                    }
                }
            }
        }
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Initialize -> handleSystemState(
                hasLocationPermission = intent.hasLocationPermission,
                isLocationEnabled = intent.isLocationEnabled,
                shouldLoad = true
            )

            is HomeIntent.PermissionResult -> handleSystemState(
                hasLocationPermission = intent.granted,
                isLocationEnabled = intent.isLocationEnabled,
                shouldLoad = intent.granted && intent.isLocationEnabled
            )

            is HomeIntent.SystemStatusChanged -> handleSystemState(
                hasLocationPermission = intent.hasLocationPermission,
                isLocationEnabled = intent.isLocationEnabled,
                shouldLoad = state.value.weather == null || state.value.isUsingCachedData
            )

            HomeIntent.Refresh -> loadWeatherByCurrentLocation(isRefresh = true)
            HomeIntent.Retry -> loadWeatherByCurrentLocation()
        }
    }

    private fun handleSystemState(
        hasLocationPermission: Boolean,
        isLocationEnabled: Boolean,
        shouldLoad: Boolean
    ) {
        setState {
            copy(
                isSystemStateKnown = true,
                hasLocationPermission = hasLocationPermission,
                isLocationEnabled = isLocationEnabled,
                error = when {
                    !hasLocationPermission -> HomeError.PermissionDenied
                    !isLocationEnabled -> HomeError.LocationDisabled
                    weather != null && error is HomeError.NoInternet -> error
                    else -> null
                }
            )
        }

        if (hasLocationPermission && isLocationEnabled && shouldLoad) {
            loadWeatherByCurrentLocation()
        }
    }

    private fun loadWeatherByCurrentLocation(isRefresh: Boolean = false) {
        val currentState = state.value
        if (!currentState.hasLocationPermission) {
            setState { copy(error = HomeError.PermissionDenied) }
            return
        }
        if (!currentState.isLocationEnabled) {
            setState { copy(error = HomeError.LocationDisabled) }
            return
        }

        viewModelScope.launch {
            setState {
                copy(
                    isLoading = weather == null,
                    isRefreshing = isRefresh && weather != null,
                    error = null
                )
            }
            when (val locationResult = getCurrentLocationUseCase()) {
                is ResultState.Error -> {
                    val savedLocation = baseWeatherSetup.getSavedLocation()
                    if (savedLocation != null) {
                        loadWeather(
                            lat = savedLocation.latitude,
                            lon = savedLocation.longitude
                        )
                    } else {
                        setState {
                            copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = HomeError.Unknown(locationResult.message),
                                isUsingCachedData = weather != null
                            )
                        }
                    }
                }

                is ResultState.Success -> {
                    baseWeatherSetup.saveLastLocation(locationResult.data)
                    loadWeather(
                        lat = locationResult.data.latitude,
                        lon = locationResult.data.longitude
                    )
                }

                else -> setState { copy(isLoading = false, isRefreshing = false) }
            }
        }
    }

    private suspend fun loadWeather(
        lat: Double,
        lon: Double
    ) {
        val currentState = state.value
        when (val weatherResult = getCurrentWeatherUseCase(lat = lat, lon = lon)) {
            is ResultState.Error -> {
                if (currentState.weather != null) {
                    setState {
                        copy(
                            isLoading = false,
                            isRefreshing = false,
                            isUsingCachedData = true,
                            error = mapError(weatherResult)
                        )
                    }
                } else {
                    val cachedWeather = getCachedWeatherUseCase()
                    val cachedForecast = baseWeatherSetup.getCachedForecastData()
                    if (cachedWeather != null) {
                        setState {
                            copy(
                                isLoading = false,
                                isRefreshing = false,
                                weather = cachedWeather.mergeDailyTemperatureRange(cachedForecast),
                                lastKnownWeather = cachedWeather.mergeDailyTemperatureRange(cachedForecast),
                                weatherDetails = cachedForecast?.details,
                                forecast = cachedForecast?.daily.orEmpty(),
                                hourlyForecast = cachedForecast?.hourly.orEmpty(),
                                isUsingCachedData = true,
                                error = mapError(weatherResult)
                            )
                        }
                        return
                    }
                    setState {
                        copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = mapError(weatherResult),
                            isUsingCachedData = false
                        )
                    }
                }
            }

            is ResultState.Success -> {
                when (val forecastResult = getForecastUseCase(lat = lat, lon = lon)) {
                    is ResultState.Success -> {
                        val forecastData = forecastResult.data
                        val updatedWeather = weatherResult.data.mergeDailyTemperatureRange(forecastData)
                        baseWeatherSetup.saveLastWeather(updatedWeather)
                        baseWeatherSetup.saveLastForecastData(forecastData)
                        setState {
                            copy(
                                isLoading = false,
                                isRefreshing = false,
                                weather = updatedWeather,
                                lastKnownWeather = updatedWeather,
                                weatherDetails = forecastData.details,
                                forecast = forecastData.daily,
                                hourlyForecast = forecastData.hourly,
                                isUsingCachedData = false,
                                error = null
                            )
                        }
                    }

                    is ResultState.Error -> {
                        if (currentState.weather != null) {
                            setState {
                                copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    isUsingCachedData = true,
                                    error = mapError(forecastResult)
                                )
                            }
                        } else {
                            val updatedWeather = weatherResult.data
                            baseWeatherSetup.saveLastWeather(updatedWeather)
                            setState {
                            copy(
                                isLoading = false,
                                isRefreshing = false,
                                weather = updatedWeather,
                                lastKnownWeather = updatedWeather,
                                weatherDetails = null,
                                forecast = emptyList(),
                                hourlyForecast = emptyList(),
                                isUsingCachedData = false,
                                    error = mapError(forecastResult)
                                )
                            }
                        }
                    }

                    else -> setState { copy(isLoading = false, isRefreshing = false) }
                }
            }

            else -> setState { copy(isLoading = false, isRefreshing = false) }
        }
    }

    private fun mapError(error: ResultState.Error): HomeError =
        when (val throwable = error.throwable) {
            is NetworkException.NoInternet -> HomeError.NoInternet
            is NetworkException.Http -> HomeError.Server(throwable.code)
            else -> HomeError.Unknown(error.message)
        }
}
