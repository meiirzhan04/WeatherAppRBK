package edu.learn.weatherapprbk.data.repository

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.core.repository.BaseRepository
import edu.learn.weatherapprbk.core.repository.mappedApiCallList
import edu.learn.weatherapprbk.data.remote.api.WeatherService
import edu.learn.weatherapprbk.data.remote.mapper.CityMapper
import edu.learn.weatherapprbk.domain.model.City
import edu.learn.weatherapprbk.domain.repository.CityRepository

class CityRepositoryImpl(
    private val api: WeatherService,
    private val mapper: CityMapper
): CityRepository, BaseRepository {
    override suspend fun getListOfCities(cityName: String): ResultState<List<City>> {
        return mappedApiCallList(mapper) {
            api.getListOfCities(cityName)
        }
    }
}