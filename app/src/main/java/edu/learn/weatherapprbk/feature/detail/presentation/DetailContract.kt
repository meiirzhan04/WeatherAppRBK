package edu.learn.weatherapprbk.feature.detail.presentation

import androidx.compose.runtime.Immutable
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailCityCardUi
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailSheetAction

@Immutable
data class DetailState(
    val isInitialized: Boolean = false,
    val isLoading: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
    val searchQuery: String = "",
    val allCards: List<DetailCityCardUi> = emptyList(),
    val error: DetailError? = null
) {
    val visibleCards: List<DetailCityCardUi>
        get() {
            if (searchQuery.isBlank()) return allCards
            return allCards.filter { card ->
                card.cityName.contains(searchQuery.trim(), ignoreCase = true)
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
}

sealed interface DetailEffect
sealed interface DetailError {
    data class Unknown(val message: String) : DetailError
}

internal fun detailMockCards(): List<DetailCityCardUi> = listOf(
    DetailCityCardUi(
        cityId = "almaty",
        cityName = "Almaty",
        latitude = 43.238949,
        longitude = 76.889709,
        time = "15:51",
        temperature = "11",
        condition = "Mostly sunny",
        min = "3",
        max = "11",
        backgroundRes = R.drawable.day
    ),
    DetailCityCardUi(
        cityId = "astana",
        cityName = "Astana",
        latitude = 51.169392,
        longitude = 71.449074,
        time = "15:51",
        temperature = "9",
        condition = "Mostly sunny",
        min = "1",
        max = "10",
        backgroundRes = R.drawable.dark
    )
)
