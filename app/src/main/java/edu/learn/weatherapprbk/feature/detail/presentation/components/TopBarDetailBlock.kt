package edu.learn.weatherapprbk.feature.detail.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import edu.learn.resources.components.safeClickable
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R

@Composable
fun TopBarDetailBlock(
    modifier: Modifier = Modifier,
    onAction: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = WeatherAppRBKTheme.dimensions.screenHorizontalPadding,
                end = WeatherAppRBKTheme.dimensions.screenHorizontalPadding,
                top = WeatherAppRBKTheme.dimensions.topBarTopInset
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.weather_title),
            style = WeatherAppRBKTheme.typography.weight700Size32LineHeight38,
            color = WeatherAppRBKTheme.colors.textPrimary
        )
        Image(
            painter = painterResource(R.drawable.ic_tabbar_detail),
            contentDescription = "",
            modifier = Modifier.safeClickable { onAction() }
        )
    }
}
