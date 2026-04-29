package edu.learn.weatherapprbk.feature.detail.presentation

import androidx.lifecycle.viewModelScope
import edu.learn.weatherapprbk.core.architecture.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailViewModel : BaseViewModel<DetailIntent, DetailState, DetailEffect>(
    initialState = DetailState()
) {
    override fun onIntent(intent: DetailIntent) {
        when (intent) {
            DetailIntent.Initialize -> {
                if (!state.value.isInitialized && !state.value.isLoading) {
                    loadDetailCards()
                }
            }

            DetailIntent.Retry -> loadDetailCards()
            is DetailIntent.OnSearchQueryChanged -> {
                setState {
                    copy(
                        searchQuery = intent.query
                    )
                }
            }

            DetailIntent.OpenBottomSheet -> {
                if (state.value.visibleCards.isNotEmpty()) {
                    setState { copy(isBottomSheetVisible = true) }
                }
            }

            DetailIntent.CloseBottomSheet,
            is DetailIntent.OnSheetActionClick -> {
                setState { copy(isBottomSheetVisible = false) }
            }
        }
    }

    private fun loadDetailCards() {
        if (state.value.isLoading) return

        viewModelScope.launch {
            setState {
                copy(
                    isInitialized = true,
                    isLoading = true,
                    isBottomSheetVisible = false,
                    error = null
                )
            }

            runCatching {
                delay(250)
                detailMockCards()
            }.onSuccess { cards ->
                setState {
                    copy(
                        isLoading = false,
                        allCards = cards,
                        error = null
                    )
                }
            }.onFailure { throwable ->
                setState {
                    copy(
                        isLoading = false,
                        allCards = emptyList(),
                        error = DetailError.Unknown(throwable.message.orEmpty())
                    )
                }
            }
        }
    }
}
