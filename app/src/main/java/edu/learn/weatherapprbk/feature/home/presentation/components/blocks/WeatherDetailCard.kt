package edu.learn.weatherapprbk.feature.home.presentation.components.blocks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.feature.home.presentation.components.weatherGlassCard


@Composable
fun WeatherDetailCard(
    title: String,
    iconRes: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .weatherGlassCard()
            .defaultMinSize(minHeight = 160.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WeatherAppRBKTheme.dimensions.medium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.60f))
                )
                Text(
                    text = title,
                    style = WeatherAppRBKTheme.typography.weight500Size12LineHeight16,
                    color = Color.White.copy(alpha = 0.60f)
                )
            }
            Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.smallMedium))
            content()
        }
    }
}