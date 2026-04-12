package edu.learn.weatherapprbk.di.module

import edu.learn.weatherapprbk.feature.home.presentation.HomeViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val homeModule = module {
    viewModel { HomeViewModel(getCurrentWeatherUseCase = get(), getCurrentLocationUseCase = get()) }
}