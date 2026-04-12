package edu.learn.weatherapprbk.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import edu.learn.weatherapprbk.core.architecture.ResultState
import edu.learn.weatherapprbk.domain.model.UserLocation
import edu.learn.weatherapprbk.domain.repository.LocationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class LocationRepositoryImpl(
    context: Context
) : LocationRepository {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): ResultState<UserLocation> =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        continuation.resume(
                            ResultState.Success(
                                UserLocation(
                                    latitude = location.latitude,
                                    longitude = location.longitude
                                )
                            )
                        )
                    } else {
                        requestFreshLocation(continuation)
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resume(ResultState.Error(exception.message.orEmpty()))
                }
        }

    @SuppressLint("MissingPermission")
    private fun requestFreshLocation(
        continuation: Continuation<ResultState<UserLocation>>
    ) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10_000L
        )
            .setWaitForAccurateLocation(true)
            .setMinUpdateIntervalMillis(5_000L)
            .setMaxUpdates(1)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                fusedLocationClient.removeLocationUpdates(this)
                val location = result.lastLocation
                if (location != null) {
                    continuation.resume(
                        ResultState.Success(
                            UserLocation(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        )
                    )
                } else {
                    continuation.resume(ResultState.Error(message = "Location is null"))
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
    }
}