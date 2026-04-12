package edu.learn.weatherapprbk.domain.repository

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.UserLocation

interface LocationRepository {
    suspend fun getCurrentLocation(): ResultState<UserLocation>
}