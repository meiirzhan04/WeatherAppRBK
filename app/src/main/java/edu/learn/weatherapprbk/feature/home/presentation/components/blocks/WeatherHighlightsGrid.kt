package edu.learn.weatherapprbk.feature.home.presentation.components.blocks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.learn.resources.datetime.WeatherDateTimeFormatter
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.domain.model.ForecastDay
import edu.learn.weatherapprbk.domain.model.WeatherDetails
import edu.learn.weatherapprbk.domain.model.WeatherInfo
import edu.learn.weatherapprbk.feature.home.presentation.components.UvIndexBar
import edu.learn.weatherapprbk.feature.home.presentation.components.WeatherMetricRow
import edu.learn.weatherapprbk.feature.home.presentation.components.averageMaxDiffText
import edu.learn.weatherapprbk.feature.home.presentation.components.averageMaxMessage
import edu.learn.weatherapprbk.feature.home.presentation.components.feelsLikeMessage
import edu.learn.weatherapprbk.feature.home.presentation.components.formatTemperature
import edu.learn.weatherapprbk.feature.home.presentation.components.metersPerSecondToKmPerHour
import edu.learn.weatherapprbk.feature.home.presentation.components.uvLevelLabel
import edu.learn.weatherapprbk.feature.home.presentation.components.windDirectionLabel
import kotlin.math.roundToInt

@Composable
fun WeatherHighlightsGrid(
    weather: WeatherInfo,
    details: WeatherDetails,
    forecast: List<ForecastDay>,
    modifier: Modifier = Modifier
) {
    val averageMaxValue = forecast.map { it.maxTemp }.average()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WeatherDetailCard(
                title = stringResource(R.string.in_middle),
                iconRes = R.drawable.ic_graph,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Text(
                    text = averageMaxDiffText(weather.temperature, weather.tempAvg),
                    style = WeatherAppRBKTheme.typography.weight500Size32LineHeight38,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.extraSmall))
                Text(
                    text = averageMaxMessage(weather.temperature, weather.tempAvg),
                    style = WeatherAppRBKTheme.typography.weight500Size15LineHeight20,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.today),
                        style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                        color = WeatherAppRBKTheme.colors.textSecondary
                    )
                    Text(
                        text = stringResource(R.string.details_max_label, formatTemperature(weather.tempMax)),
                        style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                        color = WeatherAppRBKTheme.colors.textSecondary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.details_average),
                        style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                        color = WeatherAppRBKTheme.colors.textSecondary
                    )
                    Text(
                        text = stringResource(R.string.details_max_label, formatTemperature(weather.tempAvg)),
                        style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                        color = WeatherAppRBKTheme.colors.textSecondary
                    )
                }
            }
            WeatherDetailCard(
                title = stringResource(R.string.details_feels_like),
                iconRes = R.drawable.ic_thermometer,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Text(
                    text = formatTemperature(details.feelsLike),
                    style = WeatherAppRBKTheme.typography.weight500Size32LineHeight38,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.small))
                Text(
                    text = feelsLikeMessage(details.feelsLike, weather.temperature),
                    style = WeatherAppRBKTheme.typography.weight400Size14LineHeight20,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
            }
        }
        WeatherDetailCard(
            title = stringResource(R.string.details_wind),
            iconRes = R.drawable.ic_wind,
            modifier = Modifier.fillMaxWidth()
        ) {
            WeatherMetricRow(
                label = stringResource(R.string.details_wind_speed_label),
                value = stringResource(
                    R.string.details_speed_unit,
                    metersPerSecondToKmPerHour(details.windSpeed)
                )
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White.copy(alpha = 0.10f),
                modifier = Modifier.padding(vertical = WeatherAppRBKTheme.dimensions.extraMedium)
            )
            WeatherMetricRow(
                label = stringResource(R.string.details_wind_gust_label),
                value = stringResource(
                    R.string.details_speed_unit,
                    metersPerSecondToKmPerHour(details.windGust)
                )
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White.copy(alpha = 0.10f),
                modifier = Modifier.padding(vertical = WeatherAppRBKTheme.dimensions.extraMedium)
            )
            WeatherMetricRow(
                label = stringResource(R.string.details_wind_direction_label),
                value = windDirectionLabel(details.windDirectionDegrees)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WeatherDetailCard(
                title = stringResource(R.string.details_uv_index),
                iconRes = R.drawable.ic_sun,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = details.uvIndex.roundToInt().toString(),
                    style = WeatherAppRBKTheme.typography.weight600Size42LineHeight48,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.extraSmall))
                Text(
                    text = uvLevelLabel(details.uvIndex),
                    style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.extraSmall))
                UvIndexBar(progress = (details.uvIndex / 11f).coerceIn(0.0, 1.0).toFloat())
                Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.extraSmall))
                Text(
                    text = stringResource(R.string.details_uv_message, uvLevelLabel(details.uvIndex).lowercase()),
                    style = WeatherAppRBKTheme.typography.weight400Size16LineHeight21,
                    color = WeatherAppRBKTheme.colors.textSecondary
                )
            }
            WeatherDetailCard(
                title = stringResource(R.string.details_sunset),
                iconRes = R.drawable.ic_sunset_with_shadow,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = WeatherDateTimeFormatter.timeLabel(timestampSeconds = details.sunsetEpochSeconds, timezoneOffsetSeconds = weather.timezoneOffsetSeconds),
                    style = WeatherAppRBKTheme.typography.weight600Size42LineHeight48,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        R.string.details_sunrise_label,
                        WeatherDateTimeFormatter.timeLabel(
                            timestampSeconds = details.sunriseEpochSeconds,
                            timezoneOffsetSeconds = weather.timezoneOffsetSeconds
                        )
                    ),
                    style = WeatherAppRBKTheme.typography.weight400Size16LineHeight21,
                    color = WeatherAppRBKTheme.colors.textSecondary
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WeatherDetailCard(
                title = stringResource(R.string.details_humidity),
                iconRes = R.drawable.ic_humidity,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.details_humidity_unit, details.humidity),
                    style = WeatherAppRBKTheme.typography.weight600Size42LineHeight48,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.details_humidity_caption,
                        formatTemperature(details.dewPoint)
                    ),
                    style = WeatherAppRBKTheme.typography.weight400Size14LineHeight20,
                    color = WeatherAppRBKTheme.colors.textSecondary
                )
            }
            WeatherDetailCard(
                title = stringResource(R.string.details_pressure),
                iconRes = R.drawable.ic_squeeze,
                modifier = Modifier.weight(1f).fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.details_pressure_unit, details.pressure),
                    style = WeatherAppRBKTheme.typography.weight600Size34LineHeight41,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
                Spacer(Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "",
                        tint = WeatherAppRBKTheme.colors.textSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = stringResource(R.string.details_pressure_unit_hpa),
                        style = WeatherAppRBKTheme.typography.weight400Size16LineHeight21,
                        color = WeatherAppRBKTheme.colors.textSecondary
                    )
                }
            }
        }
    }
}
