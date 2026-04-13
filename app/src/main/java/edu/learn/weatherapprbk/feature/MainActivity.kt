package edu.learn.weatherapprbk.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.feature.home.presentation.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppRBKTheme {
                HomeScreen()
            }
        }
    }
}