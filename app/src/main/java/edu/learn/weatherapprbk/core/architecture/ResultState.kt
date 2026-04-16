package edu.learn.weatherapprbk.core.architecture

sealed interface ResultState<out T> {

    data object Idle : ResultState<Nothing>

    data object Loading : ResultState<Nothing>

    data class Success<T>(
        val data: T
    ) : ResultState<T>

    data class Error(
        val message: String,
        val throwable: Throwable? = null,
        val code: Int? = null
    ) : ResultState<Nothing>
}