package edu.learn.weatherapprbk.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppRBKTheme {
                AppNavHost()
            }
        }
    }
}