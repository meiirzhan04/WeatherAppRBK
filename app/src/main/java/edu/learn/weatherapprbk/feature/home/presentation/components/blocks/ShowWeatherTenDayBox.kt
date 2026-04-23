package edu.learn.weatherapprbk.feature.home.presentation.components.blocks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.domain.model.ForecastDay
import edu.learn.weatherapprbk.feature.home.presentation.WeatherVisualResolver.resolveHourlyWeatherIcon
import edu.learn.weatherapprbk.feature.home.presentation.components.TemperatureRangeBar
import edu.learn.weatherapprbk.feature.home.presentation.components.weatherGlassCard
import kotlin.math.max

@Composable
fun ShowWeatherTenDayBox(
    forecast: List<ForecastDay>,
    modifier: Modifier = Modifier
) {
    if (forecast.isEmpty()) return

    val globalMin = forecast.minOf { it.minTemp }
    val globalMax = forecast.maxOf { it.maxTemp }
    val temperatureSpan = max(1, globalMax - globalMin)
    Box(
        modifier = modifier
            .weatherGlassCard()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = WeatherAppRBKTheme.dimensions.medium,
                    vertical = WeatherAppRBKTheme.dimensions.extraMedium
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = stringResource(R.string.forecast_icon_content_description),
                    modifier = Modifier.size(14.dp),
                    colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.55f))
                )
                Text(
                    text = stringResource(R.string.forecast_title),
                    style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                    color = Color.White.copy(alpha = 0.55f)
                )
            }
            forecast.forEach { item ->
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White.copy(alpha = 0.14f),
                    modifier = Modifier.padding(top = WeatherAppRBKTheme.dimensions.extraMedium)
                )
                ForecastDayRow(
                    item = item,
                    globalMin = globalMin,
                    temperatureSpan = temperatureSpan,
                    modifier = Modifier.padding(top = WeatherAppRBKTheme.dimensions.extraMedium)
                )
            }
        }
    }
}


@Composable
private fun ForecastDayRow(
    item: ForecastDay,
    globalMin: Int,
    temperatureSpan: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(38.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (item.isToday) stringResource(R.string.today) else item.dateLabel,
            modifier = Modifier.width(70.dp),
            style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
            color = WeatherAppRBKTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.width(WeatherAppRBKTheme.dimensions.mediumMedium))
        Image(
            painter = painterResource(resolveHourlyWeatherIcon(item.condition, item.iconCode)),
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.temperature_degree, item.minTemp),
            modifier = Modifier.width(32.dp),
            style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
            color = Color.White.copy(alpha = 0.56f)
        )
        Spacer(modifier = Modifier.width(WeatherAppRBKTheme.dimensions.smallMedium))
        val startFraction = (item.minTemp - globalMin).toFloat() / temperatureSpan
        val endFraction = (item.maxTemp - globalMin).toFloat() / temperatureSpan
        TemperatureRangeBar(
            startFraction = startFraction,
            endFraction = endFraction,
            modifier = Modifier.width(96.dp)
        )
        Spacer(modifier = Modifier.width(WeatherAppRBKTheme.dimensions.extraMedium))
        Text(
            text = stringResource(R.string.temperature_degree, item.maxTemp),
            modifier = Modifier.width(32.dp),
            style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
            color = WeatherAppRBKTheme.colors.textPrimary
        )
    }
}
