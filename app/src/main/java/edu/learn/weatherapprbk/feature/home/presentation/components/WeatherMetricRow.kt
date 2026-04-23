package edu.learn.weatherapprbk.feature.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import edu.learn.resources.theme.WeatherAppRBKTheme

@Composable
fun WeatherMetricRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
            color = WeatherAppRBKTheme.colors.textPrimary
        )
        Text(
            text = value,
            style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
            color = WeatherAppRBKTheme.colors.textSecondary
        )
    }
}