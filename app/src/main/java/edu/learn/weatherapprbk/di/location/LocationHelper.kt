package edu.learn.weatherapprbk.di.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationHelper(
    context: Context
) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        onSuccess: (lat: Double, lon: Double) -> Unit,
        onError: (String) -> Unit
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onSuccess(location.latitude, location.longitude)
                } else {
                    onError("Location is null")
                }
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Failed to get location")
            }
    }
}