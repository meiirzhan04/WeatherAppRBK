package edu.learn.weatherapprbk.core.repository

import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.core.mapper.BaseMapper
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

interface BaseRepository {
    fun <R> handleSuccess(result: R): ResultState<R> {
        return ResultState.Success(result)
    }
    fun <R> handleError(throwable: Throwable): ResultState<R> {
        return when (val domainThrowable = throwable.toDomainThrowable()) {
            is NetworkException.Http -> ResultState.Error(
                message = domainThrowable.message ?: "Http error",
                throwable = domainThrowable,
                code = domainThrowable.code
            )
            else -> ResultState.Error(message = domainThrowable.message ?: "Unknown error", throwable = domainThrowable)
        }
    }
}

suspend inline fun <reified T> BaseRepository.apiCall(
    crossinline request: suspend () -> HttpResponse
): ResultState<T> =
    runCatching {
        val response = request()
        val code = response.status.value

        if (code !in 200..299) {
            throw ApiException(
                code = code,
                errorBody = runCatching { response.bodyAsText() }.getOrNull()
            )
        }
        response.body<T>()
    }.fold(
        onSuccess = ::handleSuccess,
        onFailure = ::handleError
    )

suspend fun <FROM, TO> BaseRepository.mappedApiCall(mapper: BaseMapper<FROM, TO>, call: suspend () -> FROM): ResultState<TO> =
    runCatching { mapper.map(call()) }.fold(onSuccess = ::handleSuccess, onFailure = ::handleError)

suspend fun <FROM, TO> BaseRepository.mappedApiCallList(mapper: BaseMapper<FROM, TO>, call: suspend () -> List<FROM>): ResultState<List<TO>> =
    runCatching { call().map(mapper::map) }.fold(onSuccess = ::handleSuccess, onFailure = ::handleError)
private fun Throwable.toDomainThrowable(): Throwable = when (this) {
    is ApiException -> NetworkException.Http(code = this.code, errorMessage = this.errorBody.orEmpty())
    is ResponseException -> NetworkException.Http(code = this.response.status.value, errorMessage = null)
    is HttpRequestTimeoutException -> NetworkException.Timeout
    is IOException -> NetworkException.NoInternet
    is SerializationException -> NetworkException.Serialization(message)
    else -> NetworkException.Unknown(message)
}