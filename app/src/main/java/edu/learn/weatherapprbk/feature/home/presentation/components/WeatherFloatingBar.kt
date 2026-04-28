package edu.learn.weatherapprbk.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Navigation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import edu.learn.resources.components.safeClickable
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R

@Composable
fun WeatherFloatingBar(
    modifier: Modifier = Modifier,
    currentPage: Int,
    totalPage: Int,
    onMapClick: () -> Unit,
    onListClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SecondGlassBoxWithIcon(icon = R.drawable.ic_map, onClick = onMapClick)
        GlassCapsule {
            Icon(
                imageVector = Icons.Rounded.Navigation,
                contentDescription = "",
                tint = if (currentPage == 0) Color.White else Color.White.copy(0.4f),
                modifier = Modifier.size(10.dp)
            )
            repeat(totalPage - 1) { index ->
                Box(
                    modifier = Modifier
                        .size(if (index + 1 == currentPage) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (index + 1 == currentPage) Color.White else Color.White.copy(0.4f))
                )
            }
        }
        SecondGlassBoxWithIcon(icon = R.drawable.ic_list, onClick = onListClick)
    }
}

@Composable
private fun SecondGlassBoxWithIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                ambientColor = Color(0xFF000000).copy(alpha = 0.16f),
                spotColor = Color(0xFF000000).copy(alpha = 0.16f)
            )
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.00f to Color(0xFF0C2741).copy(alpha = 0f),
                        0.52f to Color(0xFF0C2741).copy(alpha = 0.25f)
                    )
                ),
                shape = CircleShape
            )
            .blur(0.5.dp)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.52f),
                shape = CircleShape
            )
            .safeClickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Composable
private fun GlassCapsule(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(50),
                ambientColor = Color.Black.copy(alpha = 0.16f),
                spotColor = Color.Black.copy(alpha = 0.16f)
            )
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.00f to Color(0xFF0C2741).copy(alpha = 0f),
                        0.52f to Color(0xFF0C2741).copy(alpha = 0.25f)
                    )
                ),
                shape = RoundedCornerShape(50)
            )
            .blur(0.5.dp)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.52f),
                shape = RoundedCornerShape(50)
            )
            .padding(vertical = WeatherAppRBKTheme.dimensions.medium, horizontal = WeatherAppRBKTheme.dimensions.mediumSmall),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}