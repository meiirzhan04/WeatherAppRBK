package edu.learn.weatherapprbk.feature.detail.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import edu.learn.resources.components.safeClickable
import edu.learn.resources.theme.WeatherAppRBKTheme

@Composable
fun CitiesCardBlock(
    cityName: String,
    time: String,
    temperature: String,
    condition: String,
    min: String,
    max: String,
    backgroundRes: Int,
    onAction: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .clip(RoundedCornerShape(WeatherAppRBKTheme.dimensions.cardCornerRadius))
            .background(WeatherAppRBKTheme.colors.whiteTransparent)
            .safeClickable { onAction() }
    ) {
        Image(
            painter = painterResource(backgroundRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = WeatherAppRBKTheme.dimensions.medium,
                    vertical = WeatherAppRBKTheme.dimensions.extraMedium
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = cityName,
                        style = WeatherAppRBKTheme.typography.weight600Size20LineHeight25,
                        color = WeatherAppRBKTheme.colors.textPrimary
                    )

                    Text(
                        text = time,
                        style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                        color = WeatherAppRBKTheme.colors.textSecondary
                    )
                }

                Text(
                    text = condition,
                    style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                    color = WeatherAppRBKTheme.colors.textSecondary
                )
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$temperature\u00B0",
                    style = WeatherAppRBKTheme.typography.weight300Size42LineHeight48LetterSpacing4,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )

                Row {
                    Text(
                        text = "Max.: $max\u00B0, ",
                        style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                        color = WeatherAppRBKTheme.colors.textPrimary
                    )

                    Text(
                        text = "Min.: $min\u00B0",
                        style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                        color = WeatherAppRBKTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}
