package edu.learn.weatherapprbk.data.remote.mapper

import edu.learn.weatherapprbk.core.mapper.BaseMapper
import edu.learn.weatherapprbk.data.remote.dto.CityDto
import edu.learn.weatherapprbk.domain.model.City

class CityMapper : BaseMapper<CityDto, City> {
    override fun map(source: CityDto): City {
        return City(
            name = source.name,
            country = source.country,
            state = source.state,
            latitude = source.lat,
            longitude = source.lon
        )
    }
}