package edu.learn.weatherapprbk.feature.home.presentation.components.blocks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp as lerpFloat
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R

@Composable
fun HeaderBlock(
    title: String,
    cityName: String,
    degree: String,
    conditionText: String,
    max: String,
    min: String,
    collapseProgress: Float = 0f,
    modifier: Modifier = Modifier
) {
    val progress = collapseProgress.coerceIn(0f, 1f)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                alpha = (1f - progress * 1.15f).coerceIn(0f, 1f)
                translationY = -56.dp.toPx() * progress
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.alpha((1f - progress * 1.2f).coerceIn(0f, 1f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(WeatherAppRBKTheme.dimensions.extraExtraSmall)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = ""
            )
            Spacer(Modifier.width(WeatherAppRBKTheme.dimensions.extraExtraSmall))
            Text(
                text = title,
                style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                color = WeatherAppRBKTheme.colors.textPrimary
            )
        }
        Text(
            text = cityName,
            style = WeatherAppRBKTheme.typography.weight500Size32LineHeight38,
            color = WeatherAppRBKTheme.colors.textPrimary,
            modifier = Modifier.alpha((1f - progress).coerceIn(0f, 1f))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(lerp(start = 180.dp, stop = 0.dp, fraction = progress))
                .clipToBounds(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = degree,
                    style = WeatherAppRBKTheme.typography.weight110Size92LineHeight92,
                    color = WeatherAppRBKTheme.colors.textPrimary,
                    modifier = Modifier.graphicsLayer {
                        alpha = 1f - progress
                        scaleX = lerpFloat(1f, 0.72f, progress)
                        scaleY = lerpFloat(1f, 0.72f, progress)
                    }
                )
                Text(
                    text = conditionText,
                    style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
                    color = WeatherAppRBKTheme.colors.textSecondary,
                    modifier = Modifier.alpha(1f - progress)
                )
                Text(
                    text = stringResource(R.string.max_min_format, max, min),
                    style = WeatherAppRBKTheme.typography.weight500Size18LineHeight24,
                    color = WeatherAppRBKTheme.colors.textPrimary,
                    modifier = Modifier.alpha((1f - progress * 1.35f).coerceIn(0f, 1f))
                )
            }
        }
    }
}
