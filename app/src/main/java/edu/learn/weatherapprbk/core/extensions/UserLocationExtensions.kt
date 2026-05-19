package edu.learn.weatherapprbk.core.extensions

import android.location.Location
import edu.learn.weatherapprbk.domain.model.UserLocation

const val DEFAULT_LOCATION_THRESHOLD_METERS = 500f

fun UserLocation.distanceTo(other: UserLocation): Float {
    val results = FloatArray(1)
    Location.distanceBetween(
        latitude,
        longitude,
        other.latitude,
        other.longitude,
        results
    )
    return results[0]
}

fun UserLocation.isSameLocation(
    other: UserLocation,
    thresholdMeters: Float = DEFAULT_LOCATION_THRESHOLD_METERS
): Boolean = distanceTo(other) <= thresholdMeters
