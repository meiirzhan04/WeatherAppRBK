package edu.learn.weatherapprbk.feature.detail.presentation.components

import androidx.annotation.DrawableRes

data class DetailCityCardUi(
    val cityId: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val time: String,
    val temperature: String,
    val condition: String,
    val min: String,
    val max: String,
    @param:DrawableRes val backgroundRes: Int
)
