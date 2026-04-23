package edu.learn.weatherapprbk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.learn.weatherapprbk.data.local.entity.CachedWeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM cached_weather WHERE id = 0 LIMIT 1")
    suspend fun getWeather(): CachedWeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWeather(weather: CachedWeatherEntity)

    @Query("DELETE FROM cached_weather")
    suspend fun clear()
}
