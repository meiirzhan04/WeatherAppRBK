package edu.learn.weatherapprbk.feature.detail.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import edu.learn.weatherapprbk.R

enum class DetailSheetAction {
    EDIT_LIST,
    NOTIFICATIONS,
    CELSIUS,
    FAHRENHEIT,
    UNITS,
    REPORT_PROBLEM
}

sealed interface DetailSheetLeading {
    data class Icon(@param:DrawableRes val icon: Int) : DetailSheetLeading
    data class Text(val value: String) : DetailSheetLeading
}

data class DetailSheetItem(
    val leading: DetailSheetLeading,
    @param:StringRes val title: Int,
    val action: DetailSheetAction,
    val showDividerAfter: Boolean = false
)


val detailSheetItems = listOf(
    DetailSheetItem(
        leading = DetailSheetLeading.Icon(R.drawable.ic_pencil),
        title = R.string.edit_list,
        action = DetailSheetAction.EDIT_LIST
    ),
    DetailSheetItem(
        leading = DetailSheetLeading.Icon(R.drawable.ic_bell),
        title = R.string.notifications,
        action = DetailSheetAction.NOTIFICATIONS,
        showDividerAfter = true
    ),
    DetailSheetItem(
        leading = DetailSheetLeading.Text("°C"),
        title = R.string.celsius_degrees,
        action = DetailSheetAction.CELSIUS
    ),
    DetailSheetItem(
        leading = DetailSheetLeading.Text("°F"),
        title = R.string.fahrenheit_degrees,
        action = DetailSheetAction.FAHRENHEIT,
        showDividerAfter = true
    ),
    DetailSheetItem(
        leading = DetailSheetLeading.Icon(R.drawable.ic_colums),
        title = R.string.units,
        action = DetailSheetAction.UNITS,
        showDividerAfter = true
    ),
    DetailSheetItem(
        leading = DetailSheetLeading.Icon(R.drawable.ic_message),
        title = R.string.report_problem,
        action = DetailSheetAction.REPORT_PROBLEM
    )
)
