package edu.learn.resources.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

private val defaultDimensions = WeatherAppRBKDimensions()

val WeatherAppRBKShapes = Shapes(
    extraSmall = RoundedCornerShape(defaultDimensions.smallExtra),
    small = RoundedCornerShape(defaultDimensions.smallMedium),
    medium = RoundedCornerShape(defaultDimensions.buttonCornerRadius),
    large = RoundedCornerShape(defaultDimensions.mediumSmall),
    extraLarge = RoundedCornerShape(defaultDimensions.mediumLarge)
)
