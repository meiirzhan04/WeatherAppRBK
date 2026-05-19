package edu.learn.weatherapprbk.feature.detail.presentation

import androidx.compose.runtime.Immutable
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.domain.model.City
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailCityCardUi
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailSheetAction

@Immutable
data class DetailState(
    val isInitialized: Boolean = false,
    val isLoading: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
    val searchQuery: String = "",
    val allCards: List<DetailCityCardUi> = emptyList(),
    val cities: List<City> = emptyList(),
    val isCitySearchLoading: Boolean = false,
) {
    val visibleCards: List<DetailCityCardUi>
        get() {
            if (searchQuery.isBlank()) return allCards
            return allCards.filter { card ->
                card.cityName.contains(
                    other = searchQuery.trim(),
                    ignoreCase = true
                )
            }
        }
}

sealed interface DetailIntent {
    data object Initialize : DetailIntent
    data object Retry : DetailIntent
    data class OnSearchQueryChanged(val query: String) : DetailIntent
    data object OpenBottomSheet : DetailIntent
    data object CloseBottomSheet : DetailIntent
    data class OnSheetActionClick(val action: DetailSheetAction) : DetailIntent
    data class OnCityClick(val city: City) : DetailIntent
}

sealed interface DetailEffect