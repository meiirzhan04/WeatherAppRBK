package edu.learn.resources.datetime

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

object WeatherDateTimeFormatter {
    fun dayLabel(timestampSeconds: Long, timezoneOffsetSeconds: Int, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat("EEE", locale).apply {
            timeZone = timezoneFromOffsetSeconds(timezoneOffsetSeconds)
        }
        val value = formatter.format(Date(timestampSeconds * 1000))
        return value.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(locale) else char.toString()
        }
    }

    fun hourLabel(timestampSeconds: Long, timezoneOffsetSeconds: Int, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat("HH", locale).apply {
            timeZone = timezoneFromOffsetSeconds(timezoneOffsetSeconds)
        }
        return formatter.format(Date(timestampSeconds * 1000))
    }

    fun timeLabel(
        timestampSeconds: Long,
        timezoneOffsetSeconds: Int,
        locale: Locale = Locale.getDefault()
    ): String {
        val formatter = SimpleDateFormat("HH:mm", locale).apply {
            timeZone = timezoneFromOffsetSeconds(timezoneOffsetSeconds)
        }
        return formatter.format(Date(timestampSeconds * 1000))
    }

    fun timezoneFromOffsetSeconds(offsetSeconds: Int): TimeZone {
        val totalMinutes = offsetSeconds / 60
        val hours = totalMinutes / 60
        val minutes = abs(totalMinutes % 60)
        val timeZoneId = String.format(Locale.US, "GMT%+d:%02d", hours, minutes)
        return TimeZone.getTimeZone(timeZoneId)
    }
}
