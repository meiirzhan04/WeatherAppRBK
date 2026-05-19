package edu.learn.weatherapprbk.feature.home.presentation.components.blocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import edu.learn.resources.theme.WeatherAppRBKTheme

@Composable
fun TemperatureRangeBar(
    startFraction: Float,
    endFraction: Float,
    modifier: Modifier = Modifier
) {
    val color = WeatherAppRBKTheme.colors.secondary
    Canvas(
        modifier = modifier
            .height(WeatherAppRBKTheme.dimensions.small)
            .padding(vertical = WeatherAppRBKTheme.dimensions.hairline)
    ) {
        val radius = CornerRadius(size.height / 2f, size.height / 2f)
        drawRoundRect(color = Color(0x14000000), cornerRadius = radius)
        var barStart = size.width * startFraction.coerceIn(0f, 1f)
        var barWidth = size.width * (endFraction.coerceIn(startFraction.coerceIn(0f, 1f), 1f) - startFraction.coerceIn(0f, 1f))
        if (barWidth == 0f) {
            barWidth = size.height
            barStart = (barStart - barWidth / 2f).coerceIn(0f, size.width - barWidth)
        }

        drawRoundRect(
            color = color,
            topLeft = Offset(barStart, 0f),
            size = Size(width = minOf(barWidth, size.width - barStart), height = size.height),
            cornerRadius = radius
        )
    }
}
