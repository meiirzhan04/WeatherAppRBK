package edu.learn.weatherapprbk.domain.usecase

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.UserLocation
import edu.learn.weatherapprbk.domain.repository.LocationRepository

class GetCurrentLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): ResultState<UserLocation> {
        return locationRepository.getCurrentLocation()
    }
}