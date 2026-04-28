package edu.learn.weatherapprbk.feature.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.learn.resources.components.safeClickable
import edu.learn.resources.theme.WeatherAppRBKTheme

@Composable
fun DetailBottomSheet(onAction: (DetailSheetAction) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        detailSheetItems.forEach { item ->
            DetailBottomSheetRowBlock(
                item = item,
                onClick = { onAction(item.action) }
            )
            if (item.showDividerAfter) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = WeatherAppRBKTheme.colors.textPrimary.copy(alpha = 0.12f)
                )
            }
        }
    }
}

@Composable
private fun DetailBottomSheetRowBlock(
    item: DetailSheetItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .safeClickable { onClick() }
            .padding(vertical = WeatherAppRBKTheme.dimensions.smallMedium),
        horizontalArrangement = Arrangement.spacedBy(WeatherAppRBKTheme.dimensions.extraMedium)
    ) {
        when (val leading = item.leading) {
            is DetailSheetLeading.Icon -> {
                Icon(
                    painter = painterResource(leading.icon),
                    contentDescription = "",
                    tint = WeatherAppRBKTheme.colors.textPrimary
                )
            }

            is DetailSheetLeading.Text -> {
                Text(
                    text = leading.value,
                    style = WeatherAppRBKTheme.typography.weight600Size20LineHeight25,
                    color = WeatherAppRBKTheme.colors.textPrimary
                )
            }
        }
        Text(
            text = stringResource(item.title),
            style = WeatherAppRBKTheme.typography.weight600Size16LineHeight22,
            color = WeatherAppRBKTheme.colors.textPrimary
        )
    }
}