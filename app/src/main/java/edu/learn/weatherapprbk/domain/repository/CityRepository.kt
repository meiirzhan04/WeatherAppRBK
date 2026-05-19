package edu.learn.weatherapprbk.domain.repository

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.City

interface CityRepository {
    suspend fun getListOfCities(cityName: String): ResultState<List<City>>
}