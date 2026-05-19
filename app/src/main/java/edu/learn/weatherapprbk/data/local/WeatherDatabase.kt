package edu.learn.weatherapprbk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.learn.weatherapprbk.data.local.dao.WeatherDao
import edu.learn.weatherapprbk.data.local.entity.CachedWeatherEntity

@Database(entities = [CachedWeatherEntity::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
