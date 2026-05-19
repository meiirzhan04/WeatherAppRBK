package edu.learn.weatherapprbk.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import edu.learn.resources.theme.WeatherAppRBKDimensions

private val defaultDimensions = WeatherAppRBKDimensions()
private val weatherCardShape = RoundedCornerShape(defaultDimensions.glassCardCornerRadius)

fun Modifier.weatherGlassCard(): Modifier {
    return this
        .clip(weatherCardShape)
        .background(
            brush = Brush.verticalGradient(colors = listOf(Color.White.copy(alpha = 0.20f), Color.White.copy(alpha = 0.12f)))
        )
        .border(
            width = defaultDimensions.hairline,
            color = Color.White.copy(alpha = 0.18f),
            shape = weatherCardShape
        )
}
