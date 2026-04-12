package edu.learn.weatherapprbk.core.repository

import edu.learn.weatherapprbk.core.architecture.ResultState

abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultState<T> {
        return try {
            ResultState.Success(apiCall())
        } catch (e: Exception) {
            ResultState.Error(e.message.orEmpty())
        }
    }
}