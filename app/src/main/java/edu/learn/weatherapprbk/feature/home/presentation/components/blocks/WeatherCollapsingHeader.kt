package edu.learn.weatherapprbk.feature.home.presentation.components.blocks

import android.R.attr.scaleX
import android.R.attr.scaleY
import android.R.attr.translationY
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R

@Composable
fun WeatherCollapsingHeader(
    title: String,
    cityName: String,
    degree: String,
    conditionText: String,
    max: String,
    min: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val p = progress.coerceIn(0f, 1f)
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = (1f - p * 1.15f).coerceIn(0f, 1f)
                    translationY = -24.dp.toPx() * p
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = null
                )
                Text(
                    text = title,
                    style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = cityName,
                style = WeatherAppRBKTheme.typography.weight500Size32LineHeight38,
                color = WeatherAppRBKTheme.colors.textPrimary
            )
            Text(
                text = degree,
                style = WeatherAppRBKTheme.typography.weight110Size92LineHeight92,
                color = WeatherAppRBKTheme.colors.textPrimary,
                modifier = Modifier.graphicsLayer {
                    val scale = androidx.compose.ui.util.lerp(1f, 0.74f, p)
                    scaleX = scale
                    scaleY = scale
                }
            )
            Text(
                text = conditionText,
                style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
                color = WeatherAppRBKTheme.colors.textSecondary
            )

            Text(
                text = stringResource(R.string.max_min_format, max, min),
                style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
                color = WeatherAppRBKTheme.colors.textPrimary
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    alpha = ((p - 0.45f) / 0.55f).coerceIn(0f, 1f)
                    translationY = -8.dp.toPx() * (1f - ((p - 0.45f) / 0.55f).coerceIn(0f, 1f))
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = null
                )
                Text(
                    text = title,
                    style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = cityName,
                style = WeatherAppRBKTheme.typography.weight500Size32LineHeight38,
                color = WeatherAppRBKTheme.colors.textPrimary
            )
            Text(
                text = "$degree | $conditionText",
                style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
                color = WeatherAppRBKTheme.colors.textSecondary
            )
        }
    }
}