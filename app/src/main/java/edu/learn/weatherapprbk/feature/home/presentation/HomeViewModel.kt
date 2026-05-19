package edu.learn.weatherapprbk.feature.home.presentation

import androidx.lifecycle.viewModelScope
import edu.learn.weatherapprbk.core.architecture.BaseViewModel
import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.core.repository.NetworkException
import edu.learn.weatherapprbk.core.setup.BaseWeatherSetup
import edu.learn.weatherapprbk.domain.usecase.GetCachedWeatherUseCase
import edu.learn.weatherapprbk.domain.usecase.GetCurrentLocationUseCase
import edu.learn.weatherapprbk.domain.usecase.GetCurrentWeatherUseCase
import edu.learn.weatherapprbk.domain.usecase.GetForecastUseCase
import edu.learn.weatherapprbk.feature.home.presentation.components.WeatherTarget
import kotlinx.coroutines.launch


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
            is HomeIntent.Initialize -> {
                val targetChanged = state.value.target != intent.target
                setState {
                    when {
                        !targetChanged -> copy(target = intent.target)
                        intent.target is WeatherTarget.City -> copy(
                            target = intent.target,
                            weather = null,
                            lastKnownWeather = null,
                            weatherDetails = null,
                            forecast = emptyList(),
                            hourlyForecast = emptyList(),
                            isUsingCachedData = false,
                            error = null
                        )

                        else -> copy(target = intent.target)
                    }
                }
                handleSystemState(
                    hasLocationPermission = intent.hasLocationPermission,
                    isLocationEnabled = intent.isLocationEnabled,
                    shouldLoad = true
                )
            }

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

            HomeIntent.Refresh -> loadConfiguredWeatherTarget(isRefresh = true)
            HomeIntent.Retry -> loadConfiguredWeatherTarget()
        }
    }

    private fun handleSystemState(
        hasLocationPermission: Boolean,
        isLocationEnabled: Boolean,
        shouldLoad: Boolean
    ) {
        val target = state.value.target
        setState {
            copy(
                isSystemStateKnown = true,
                hasLocationPermission = hasLocationPermission,
                isLocationEnabled = isLocationEnabled,
                error = when (target) {
                    WeatherTarget.Current -> when {
                        !hasLocationPermission -> HomeError.PermissionDenied
                        !isLocationEnabled -> HomeError.LocationDisabled
                        weather != null && error is HomeError.NoInternet -> error
                        else -> null
                    }

                    is WeatherTarget.City -> null
                }
            )
        }

        if (!shouldLoad) return

        when (target) {
            WeatherTarget.Current -> {
                if (hasLocationPermission && isLocationEnabled) {
                    loadWeatherByCurrentLocation()
                }
            }

            is WeatherTarget.City -> loadWeatherBySelectedCity(target)
        }
    }

    private fun loadConfiguredWeatherTarget(isRefresh: Boolean = false) {
        when (val target = state.value.target) {
            WeatherTarget.Current -> loadWeatherByCurrentLocation(isRefresh = isRefresh)
            is WeatherTarget.City -> loadWeatherBySelectedCity(target, isRefresh)
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
                            target = WeatherTarget.Current,
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
                        target = WeatherTarget.Current,
                        lat = locationResult.data.latitude,
                        lon = locationResult.data.longitude
                    )
                }

                else -> setState { copy(isLoading = false, isRefreshing = false) }
            }
        }
    }

    private fun loadWeatherBySelectedCity(
        target: WeatherTarget.City,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            setState {
                copy(
                    isLoading = weather == null,
                    isRefreshing = isRefresh && weather != null,
                    error = null
                )
            }
            loadWeather(
                target = target,
                lat = target.location.latitude,
                lon = target.location.longitude
            )
        }
    }

    private suspend fun loadWeather(
        target: WeatherTarget,
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
                            error = mapError(weatherResult)
                        )
                    }
                } else if (target == WeatherTarget.Current) {
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
                } else {
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
                        if (target == WeatherTarget.Current) {
                            baseWeatherSetup.saveLastWeather(updatedWeather)
                            baseWeatherSetup.saveLastForecastData(forecastData)
                        }
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
                                    error = mapError(forecastResult)
                                )
                            }
                        } else {
                            val updatedWeather = weatherResult.data
                            if (target == WeatherTarget.Current) {
                                baseWeatherSetup.saveLastWeather(updatedWeather)
                            }
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
