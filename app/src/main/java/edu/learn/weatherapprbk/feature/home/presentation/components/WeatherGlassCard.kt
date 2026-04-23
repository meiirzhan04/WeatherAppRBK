package edu.learn.weatherapprbk.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val WeatherCardShape = RoundedCornerShape(22.dp)

fun Modifier.weatherGlassCard(): Modifier {
    return this
        .clip(WeatherCardShape)
        .background(
            brush = Brush.verticalGradient(colors = listOf(Color.White.copy(alpha = 0.20f), Color.White.copy(alpha = 0.12f)))
        )
        .border(
            width = 1.dp,
            color = Color.White.copy(alpha = 0.18f),
            shape = WeatherCardShape
        )
}
