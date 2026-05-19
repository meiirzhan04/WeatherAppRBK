package edu.learn.weatherapprbk.di.module

import edu.learn.weatherapprbk.feature.detail.presentation.DetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailViewModel(getCurrentWeatherUseCase = get(), getListOfCitiesUseCase = get()) }
}
