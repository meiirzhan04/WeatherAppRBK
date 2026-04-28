package edu.learn.weatherapprbk.feature.detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.core.components.BaseBottomSheet
import edu.learn.weatherapprbk.feature.detail.presentation.components.CitiesCardBlock
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailBottomSheet
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailCityCardUi
import edu.learn.weatherapprbk.feature.detail.presentation.components.DetailSheetAction
import edu.learn.weatherapprbk.feature.detail.presentation.components.TopBarDetailBlock

@Composable
fun DetailScreen() {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    DetailScreenContent(
        onBackClick = {},
        onAction = { isBottomSheetVisible = true }
    )
    BaseBottomSheet(
        isVisible = isBottomSheetVisible,
        onDismiss = { isBottomSheetVisible = false },
        containerColor = WeatherAppRBKTheme.colors.detailBackground
    ) {
        DetailBottomSheet(
            onAction = { action ->
                when (action) {
                    DetailSheetAction.EDIT_LIST -> {}
                    DetailSheetAction.NOTIFICATIONS -> {}
                    DetailSheetAction.CELSIUS -> {}
                    DetailSheetAction.FAHRENHEIT -> {}
                    DetailSheetAction.UNITS -> {}
                    DetailSheetAction.REPORT_PROBLEM -> {}
                }
                isBottomSheetVisible = false
            }
        )
    }
}

@Composable
private fun DetailScreenContent(
    onBackClick: () -> Unit = {},
    onAction: () -> Unit = {}
) {
    val cards = listOf(
        DetailCityCardUi(
            cityName = "Almaty",
            time = "15:51",
            temperature = "11",
            condition = "Mostly sunny",
            min = "3",
            max = "11"
        ),
        DetailCityCardUi(
            cityName = "Almaty",
            time = "15:51",
            temperature = "11",
            condition = "Mostly sunny",
            min = "3",
            max = "11"
        ),
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WeatherAppRBKTheme.colors.detailBackground,
        topBar = { TopBarDetailBlock(onAction = onAction) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cards) { card ->
                CitiesCardBlock(
                    cityName = card.cityName,
                    time = card.time,
                    temperature = card.temperature,
                    condition = card.condition,
                    min = card.min,
                    max = card.max,
                    onAction = onBackClick,
                    backgroundRes = R.drawable.day
                )
            }
            item {
                Text(
                    text = stringResource(R.string.detail_more_info),
                    color = WeatherAppRBKTheme.colors.textSecondary,
                    style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}