package edu.learn.weatherapprbk.di.module
import edu.learn.weatherapprbk.data.remote.api.WeatherApi
import edu.learn.weatherapprbk.data.remote.mapper.WeatherMapper
import edu.learn.weatherapprbk.data.repository.WeatherRepositoryImpl
import edu.learn.weatherapprbk.domain.repository.WeatherRepository
import edu.learn.weatherapprbk.domain.usecase.GetCurrentWeatherUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

val weatherModule = module {
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
    //repository
    single<WeatherRepository> { WeatherRepositoryImpl(api = get(), mapper = get()) }
    //usecase
    single { GetCurrentWeatherUseCase(get()) }
}