package edu.learn.weatherapprbk.feature.detail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import edu.learn.resources.components.safeClickable
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R

@Composable
fun DetailSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onVoiceClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(WeatherAppRBKTheme.dimensions.large))
            .background(Color.White.copy(alpha = 0.06f))
            .border(
                width = WeatherAppRBKTheme.dimensions.hairline,
                color = Color.White.copy(alpha = 0.35f),
                shape = RoundedCornerShape(WeatherAppRBKTheme.dimensions.large)
            )
            .padding(
                horizontal = WeatherAppRBKTheme.dimensions.medium,
                vertical = WeatherAppRBKTheme.dimensions.extraMedium
            ),
        horizontalArrangement = Arrangement.spacedBy(WeatherAppRBKTheme.dimensions.extraMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            tint = WeatherAppRBKTheme.colors.textSecondary
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = WeatherAppRBKTheme.typography.weight400Size16LineHeight21.copy(
                color = WeatherAppRBKTheme.colors.textPrimary
            ),
            modifier = Modifier.weight(1f),
            decorationBox = { innerTextField ->
                if (value.isBlank()) {
                    Text(
                        text = stringResource(R.string.detail_search_placeholder),
                        style = WeatherAppRBKTheme.typography.weight400Size16LineHeight21,
                        color = WeatherAppRBKTheme.colors.textSecondary
                    )
                }
                innerTextField()
            }
        )
        Icon(
            imageVector = Icons.Outlined.Mic,
            contentDescription = null,
            tint = WeatherAppRBKTheme.colors.textPrimary,
            modifier = Modifier.safeClickable { onVoiceClick() }
        )
    }
}
