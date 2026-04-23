package edu.learn.weatherapprbk.di.module

import androidx.room.Room
import edu.learn.weatherapprbk.data.local.WeatherDatabase
import edu.learn.weatherapprbk.data.remote.api.WeatherApi
import edu.learn.weatherapprbk.data.remote.mapper.ForecastMapper
import edu.learn.weatherapprbk.data.remote.mapper.WeatherMapper
import edu.learn.weatherapprbk.data.repository.WeatherRepositoryImpl
import edu.learn.weatherapprbk.domain.repository.WeatherRepository
import edu.learn.weatherapprbk.domain.usecase.GetCachedWeatherUseCase
import edu.learn.weatherapprbk.domain.usecase.GetForecastUseCase
import edu.learn.weatherapprbk.domain.usecase.GetCurrentWeatherUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

val weatherModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather.db"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }
    single { get<WeatherDatabase>().weatherDao() }
    single<WeatherApi> { get<Retrofit>().create(WeatherApi::class.java) }
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single { OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>()).build() }
    single {
        Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //mapper
    single { WeatherMapper() }
    single { ForecastMapper() }
    //repository
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            api = get(),
            mapper = get(),
            forecastMapper = get(),
            weatherDao = get()
        )
    }
    //usecase
    single { GetCurrentWeatherUseCase(get()) }
    single { GetForecastUseCase(get()) }
    single { GetCachedWeatherUseCase(get()) }
}
