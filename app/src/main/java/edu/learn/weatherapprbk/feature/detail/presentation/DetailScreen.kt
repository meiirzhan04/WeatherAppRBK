package edu.learn.weatherapprbk.feature.detail.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.learn.resources.components.LoadingScreen
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.core.components.BaseBottomSheet
import edu.learn.weatherapprbk.feature.detail.presentation.components.CitiesCardBlock
import edu.learn.weatherapprbk.feature.detail.presentation.components.CitySearchResultItem
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailBottomSheet
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailCityCardUi
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailSearchBar
import edu.learn.weatherapprbk.feature.detail.presentation.components.TopBarDetailBlock
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onOpenCity: (DetailCityCardUi) -> Unit = {}
) {
    val viewModel = koinViewModel<DetailViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onIntent(DetailIntent.Initialize)
    }
    LoadingScreen(isLoading = state.isLoading) {
        when {
            state.isInitialized -> {
                DetailScreenContent(
                    state = state,
                    onIntent = viewModel::onIntent,
                    onOpenCity = onOpenCity
                )
            }
        }
    }

    BaseBottomSheet(
        isVisible = mutableStateOf(state.isBottomSheetVisible),
        onDismiss = { viewModel.onIntent(DetailIntent.CloseBottomSheet) },
        containerColor = WeatherAppRBKTheme.colors.detailBackground
    ) {
        DetailBottomSheet(
            onAction = { action -> viewModel.onIntent(DetailIntent.OnSheetActionClick(action)) }
        )
    }
}

@Composable
private fun DetailScreenContent(
    state: DetailState,
    onIntent: (DetailIntent) -> Unit,
    onOpenCity: (DetailCityCardUi) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WeatherAppRBKTheme.colors.detailBackground,
        topBar = { TopBarDetailBlock(onAction = { onIntent(DetailIntent.OpenBottomSheet) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            DetailSearchBar(
                modifier = Modifier.padding(horizontal = WeatherAppRBKTheme.dimensions.medium),
                value = state.searchQuery,
                onValueChange = { onIntent(DetailIntent.OnSearchQueryChanged(it)) }
            )
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = WeatherAppRBKTheme.dimensions.screenHorizontalPadding,
                    vertical = WeatherAppRBKTheme.dimensions.extraMedium
                ),
                verticalArrangement = Arrangement.spacedBy(WeatherAppRBKTheme.dimensions.extraMedium)
            ) {
                if (state.searchQuery.isNotBlank()) {
                    if (state.isCitySearchLoading) {
                        item {
                            Text(
                                text = "Searching...",
                                color = WeatherAppRBKTheme.colors.textSecondary,
                                style = WeatherAppRBKTheme.typography.weight400Size16LineHeight21,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = WeatherAppRBKTheme.dimensions.mediumLarge),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    items(state.cities) { city -> CitySearchResultItem(city = city, onClick = { onIntent(DetailIntent.OnCityClick(city)) }) }
                    if (!state.isCitySearchLoading && state.cities.isEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.detail_search_empty),
                                color = WeatherAppRBKTheme.colors.textSecondary,
                                style = WeatherAppRBKTheme.typography.weight400Size16LineHeight21,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = WeatherAppRBKTheme.dimensions.mediumLarge),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(state.visibleCards) { card -> CitiesCardBlock(city = card, onOpenMain = { onOpenCity(card) }) }
                    if (state.visibleCards.isNotEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.detail_more_info),
                                color = WeatherAppRBKTheme.colors.textSecondary,
                                style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = WeatherAppRBKTheme.dimensions.small),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}