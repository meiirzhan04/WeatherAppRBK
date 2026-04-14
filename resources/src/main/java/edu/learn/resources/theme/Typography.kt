package edu.learn.resources.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.learn.resources.R
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
@OptIn(ExperimentalResourceApi::class)
private fun weatherAppRBKFontFamily(): FontFamily = FontFamily(
    Font(R.font.sf_pro_400, weight = FontWeight.W400),
)

@Immutable
data class WeatherAppRBKTypography(
    val weight400Size12LineHeight16: TextStyle,
    val weight400Size13LineHeight18: TextStyle,
    val weight400Size14LineHeight20: TextStyle,
    val weight400Size16LineHeight21: TextStyle,
    val weight500Size12LineHeight16: TextStyle,
    val weight500Size13LineHeight18: TextStyle,
    val weight500Size14LineHeight20: TextStyle,
    val weight500Size15LineHeight20: TextStyle,
    val weight500Size16LineHeight21: TextStyle,
    val weight500Size17LineHeight22: TextStyle,
    val weight500Size18LineHeight24: TextStyle,
    val weight500Size22LineHeight28: TextStyle,
    val weight500Size24LineHeight30: TextStyle,
    val weight600Size16LineHeight22: TextStyle,
    val weight600Size20LineHeight25: TextStyle,
    val weight600Size28LineHeight34: TextStyle,
    val weight600Size34LineHeight41: TextStyle,
    val weight600Size42LineHeight48: TextStyle,
    val weight700Size20LineHeight28: TextStyle,
)

@Composable
@OptIn(ExperimentalResourceApi::class)
fun defaultSanaTypography(): WeatherAppRBKTypography {
    val fontFamily = weatherAppRBKFontFamily()
    return WeatherAppRBKTypography(
        weight400Size12LineHeight16 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        weight400Size13LineHeight18 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            lineHeight = 18.sp
        ),
        weight400Size14LineHeight20 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        weight400Size16LineHeight21 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            lineHeight = 21.sp
        ),
        weight500Size13LineHeight18 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 13.sp,
            lineHeight = 18.sp
        ),
        weight500Size14LineHeight20 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        weight500Size15LineHeight20 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 15.sp,
            lineHeight = 20.sp
        ),
        weight500Size16LineHeight21 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            lineHeight = 21.sp
        ),
        weight500Size17LineHeight22 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 17.sp,
            lineHeight = 22.sp
        ),
        weight500Size18LineHeight24 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ),
        weight500Size22LineHeight28 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),
        weight500Size24LineHeight30 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            lineHeight = 30.sp
        ),
        weight600Size16LineHeight22 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W600,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),
        weight600Size20LineHeight25 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            lineHeight = 25.sp
        ),
        weight600Size28LineHeight34 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W600,
            fontSize = 28.sp,
            lineHeight = 34.sp
        ),
        weight600Size42LineHeight48 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W600,
            fontSize = 42.sp,
            lineHeight = 48.sp
        ),
        weight700Size20LineHeight28 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
            lineHeight = 28.sp
        ),
        weight500Size12LineHeight16 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        weight600Size34LineHeight41 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W600,
            fontSize = 34.sp,
            lineHeight = 41.sp
        )
    )
}
val LocalWeatherAppRBKTypography = staticCompositionLocalOf<WeatherAppRBKTypography> {
    error("LocalSanaTypography is not provided. Wrap your UI with SanaTheme().")
}