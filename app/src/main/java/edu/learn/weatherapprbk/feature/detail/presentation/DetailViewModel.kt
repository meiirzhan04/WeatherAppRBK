package edu.learn.weatherapprbk.feature.detail.presentation

import androidx.lifecycle.viewModelScope
import edu.learn.weatherapprbk.core.architecture.BaseViewModel
import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.City
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import edu.learn.weatherapprbk.domain.usecase.GetCurrentWeatherUseCase
import edu.learn.weatherapprbk.domain.usecase.GetListOfCitiesUseCase
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailCityCardUi
import edu.learn.weatherapprbk.feature.home.presentation.components.WeatherVisualResolver
import edu.learn.resources.datetime.WeatherDateTimeFormatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.TimeZone
import kotlin.math.roundToInt

class DetailViewModel(
    private val getListOfCitiesUseCase: GetListOfCitiesUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : BaseViewModel<DetailIntent, DetailState, DetailEffect>(
    initialState = DetailState()
) {
    override fun onIntent(intent: DetailIntent) {
        when (intent) {
            DetailIntent.Initialize -> loadDetailCards()
            DetailIntent.Retry -> loadDetailCards()
            is DetailIntent.OnSearchQueryChanged -> {
                setState { copy(searchQuery = intent.query) }
                searchCities(intent.query)
            }
            DetailIntent.OpenBottomSheet -> setState { copy(isBottomSheetVisible = true) }
            DetailIntent.CloseBottomSheet -> setState { copy(isBottomSheetVisible = false) }
            is DetailIntent.OnSheetActionClick -> setState { copy(isBottomSheetVisible = false) }
            is DetailIntent.OnCityClick -> addCityToList(intent.city)
        }
    }

    private fun loadDetailCards() {
        if (state.value.isLoading) return
        viewModelScope.launch {
            setState { copy(isInitialized = true, isLoading = false, isBottomSheetVisible = false, allCards = emptyList()) }
        }
    }
    private fun searchCities(query: String) {
        if (query.length < 2) {
            setState { copy(cities = emptyList(), isCitySearchLoading = false) }
            return
        }
        viewModelScope.launch {
            delay(400)
            setState { copy(isCitySearchLoading = true) }
            when (val result = getListOfCitiesUseCase(query)) {
                is ResultState.Success -> setState { copy(cities = result.data, isCitySearchLoading = false) }
                is ResultState.Error -> setState { copy(cities = emptyList(), isCitySearchLoading = false) }
                else -> setState { copy(isCitySearchLoading = false) }
            }
        }
    }
    private fun addCityToList(city: City) {
        viewModelScope.launch {
            setState { copy(isLoading = true, cities = emptyList(), searchQuery = "") }
            when (val weatherResult = getCurrentWeatherUseCase(lat = city.latitude, lon = city.longitude)) {
                is ResultState.Success -> {
                    val weather = weatherResult.data
                    val card = DetailCityCardUi(
                        cityId = "${city.name}_${city.latitude}_${city.longitude}",
                        cityName = city.name,
                        latitude = city.latitude,
                        longitude = city.longitude,
                        time = formatCityTime(weather),
                        temperature = weather.temperature.roundToInt().toString(),
                        condition = weather.condition,
                        min = weather.tempMin.roundToInt().toString(),
                        max = weather.tempMax.roundToInt().toString(),
                        backgroundRes = WeatherVisualResolver.resolveBackground(
                            weather = weather,
                            currentTimeMillis = currentTimeMillisForOffset(weather.timezoneOffsetSeconds)
                        )
                    )
                    val alreadyExists = state.value.allCards.any { it.latitude == card.latitude && it.longitude == card.longitude }
                    val updatedCards = if (alreadyExists) state.value.allCards else state.value.allCards + card
                    setState { copy(isLoading = false, allCards = updatedCards) }
                }
                is ResultState.Error -> setState { copy(isLoading = false) }
                else -> {}
            }
        }
    }

    private fun formatCityTime(weather: WeatherInfo): String {
        return WeatherDateTimeFormatter.timeLabel(
            timestampSeconds = weather.updatedAtMillis / 1000,
            timezoneOffsetSeconds = weather.timezoneOffsetSeconds
        )
    }

    private fun currentTimeMillisForOffset(timezoneOffsetSeconds: Int, baseTimeMillis: Long = System.currentTimeMillis()): Long {
        val deviceOffsetSeconds = TimeZone.getDefault().getOffset(baseTimeMillis).div(1000)
        return baseTimeMillis + (timezoneOffsetSeconds - deviceOffsetSeconds) * 1000L
    }
}
