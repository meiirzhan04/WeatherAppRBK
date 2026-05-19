package edu.learn.weatherapprbk.di.module

import androidx.room.Room
import edu.learn.weatherapprbk.data.local.WeatherDatabase
import edu.learn.weatherapprbk.data.remote.api.WeatherServiceKtor
import edu.learn.weatherapprbk.data.remote.api.WeatherService
import edu.learn.weatherapprbk.data.remote.mapper.CityMapper
import edu.learn.weatherapprbk.data.remote.mapper.ForecastMapper
import edu.learn.weatherapprbk.data.remote.mapper.WeatherMapper
import edu.learn.weatherapprbk.data.repository.CityRepositoryImpl
import edu.learn.weatherapprbk.data.repository.WeatherRepositoryImpl
import edu.learn.weatherapprbk.domain.repository.CityRepository
import edu.learn.weatherapprbk.domain.repository.WeatherRepository
import edu.learn.weatherapprbk.domain.usecase.GetCachedWeatherUseCase
import edu.learn.weatherapprbk.domain.usecase.GetForecastUseCase
import edu.learn.weatherapprbk.domain.usecase.GetCurrentWeatherUseCase
import edu.learn.weatherapprbk.domain.usecase.GetListOfCitiesUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val weatherModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather.db"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }
    single { get<WeatherDatabase>().weatherDao() }
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single { OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>()).build() }
    single {
        val okHttpClient: OkHttpClient = get()
        HttpClient(OkHttp) {
            engine {
                preconfigured = okHttpClient
            }
            install(ContentNegotiation) {
                gson()
            }
            expectSuccess = true
        }
    }
    single<WeatherService> { WeatherServiceKtor(get<HttpClient>()) }
    //mapper
    single { WeatherMapper() }
    single { ForecastMapper() }
    single { CityMapper() }
    //repository
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            api = get(),
            mapper = get(),
            forecastMapper = get(),
            weatherDao = get()
        )
    }
    single<CityRepository> { CityRepositoryImpl(api = get(), mapper = get()) }
    //usecase
    single { GetCurrentWeatherUseCase(get()) }
    single { GetForecastUseCase(get()) }
    single { GetCachedWeatherUseCase(get()) }
    single { GetListOfCitiesUseCase(get()) }
}
