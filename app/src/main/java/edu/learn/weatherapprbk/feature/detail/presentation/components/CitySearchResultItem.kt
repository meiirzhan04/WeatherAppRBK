package edu.learn.weatherapprbk.feature.detail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.learn.resources.components.safeClickable
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.domain.model.City

@Composable
fun CitySearchResultItem(
    city: City,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .safeClickable { onClick() }
            .padding(WeatherAppRBKTheme.dimensions.medium)
    ) {
        Text(
            text = city.name,
            style = WeatherAppRBKTheme.typography.weight600Size20LineHeight25,
            color = WeatherAppRBKTheme.colors.textPrimary
        )

        Text(
            text = buildString {
                append(city.country)
                city.state?.let { state ->
                    append(", ")
                    append(state)
                }
            },
            style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
            color = WeatherAppRBKTheme.colors.textSecondary
        )
    }
}