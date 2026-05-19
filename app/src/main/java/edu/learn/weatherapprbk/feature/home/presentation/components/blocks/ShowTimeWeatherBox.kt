package edu.learn.weatherapprbk.feature.home.presentation.components.blocks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.domain.model.HourlyForecast
import edu.learn.weatherapprbk.feature.home.presentation.components.WeatherVisualResolver.resolveHourlyWeatherIcon
import edu.learn.weatherapprbk.feature.home.presentation.components.weatherGlassCard

@Composable
fun ShowTimeWeatherBox(
    infoText: String,
    hourlyForecast: List<HourlyForecast>
) {
    Box(
        modifier = Modifier
            .weatherGlassCard()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = WeatherAppRBKTheme.dimensions.medium)
        ) {
            Text(
                text = infoText,
                style = WeatherAppRBKTheme.typography.weight400Size14LineHeight20,
                color = WeatherAppRBKTheme.colors.textPrimary,
                modifier = Modifier.padding(horizontal = WeatherAppRBKTheme.dimensions.medium)
            )
            Spacer(Modifier.height(WeatherAppRBKTheme.dimensions.medium))
            HorizontalDivider(
                thickness = WeatherAppRBKTheme.dimensions.hairline,
                color = WeatherAppRBKTheme.colors.divider,
                modifier = Modifier.padding(horizontal = WeatherAppRBKTheme.dimensions.medium)
            )
            Spacer(Modifier.height(WeatherAppRBKTheme.dimensions.medium))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = WeatherAppRBKTheme.dimensions.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(WeatherAppRBKTheme.dimensions.mediumLarge)
            ) {
                items(hourlyForecast) { item ->
                    ColumnInfoWeather(item = item)
                }
            }
        }
    }
}
@Composable
private fun ColumnInfoWeather(item: HourlyForecast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(WeatherAppRBKTheme.dimensions.extraSmall)
    ) {
        Text(
            text = if (item.isNow) stringResource(R.string.now) else item.timeLabel,
            style = WeatherAppRBKTheme.typography.weight500Size14LineHeight20,
            color = WeatherAppRBKTheme.colors.textPrimary
        )
        Image(
            painter = painterResource(
                when {
                    item.isSunset -> R.drawable.ic_sunset
                    item.isSunrise -> R.drawable.ic_sunrise
                    else -> resolveHourlyWeatherIcon(item.condition, item.iconCode)
                }
            ),
            contentDescription = "",
            modifier = Modifier.size(WeatherAppRBKTheme.dimensions.iconLarge)
        )
        Text(
            text = when {
                item.isSunset -> stringResource(R.string.sunset)
                item.isSunrise -> stringResource(R.string.sunrise)
                else -> stringResource(R.string.temperature_degree, item.temperature)
            },
            style = WeatherAppRBKTheme.typography.weight500Size15LineHeight20,
            color = WeatherAppRBKTheme.colors.textPrimary
        )
    }
}
