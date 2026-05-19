package edu.learn.weatherapprbk.core.repository


sealed class NetworkException(
    override val message: String?
) : Throwable(message) {
    data class Http(val code: Int, private val errorMessage: String?) : NetworkException(errorMessage ?: "Http error: $code")
    data object Timeout : NetworkException("Request timeout")
    data object NoInternet : NetworkException("No internet connection")
    data class Serialization(private val errorMessage: String?) : NetworkException(errorMessage ?: "Serialization error")
    data class Unknown(private val errorMessage: String?) : NetworkException(errorMessage ?: "Unknown error")
}

class ApiException(val code: Int, val errorBody: String? = null) : Exception(errorBody ?: "Api error: $code")