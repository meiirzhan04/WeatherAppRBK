package edu.learn.weatherapprbk.domain.usecase

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.City
import edu.learn.weatherapprbk.domain.repository.CityRepository

class GetListOfCitiesUseCase(
    private val repository: CityRepository
) {
    suspend operator fun invoke(cityName: String): ResultState<List<City>> {
        return repository.getListOfCities(cityName)
    }
}