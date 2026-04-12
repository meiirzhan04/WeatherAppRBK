package edu.learn.weatherapprbk.di.module

import edu.learn.weatherapprbk.data.repository.LocationRepositoryImpl
import edu.learn.weatherapprbk.domain.repository.LocationRepository
import edu.learn.weatherapprbk.domain.usecase.GetCurrentLocationUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationModule = module {
    single<LocationRepository> {
        LocationRepositoryImpl(androidContext())
    }

    single {
        GetCurrentLocationUseCase(get())
    }
}