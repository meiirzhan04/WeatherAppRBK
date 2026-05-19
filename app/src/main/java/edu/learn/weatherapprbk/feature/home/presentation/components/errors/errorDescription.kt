package edu.learn.weatherapprbk.feature.home.presentation.components.errors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.feature.home.presentation.HomeError
import edu.learn.weatherapprbk.feature.home.presentation.HomeState

@Composable
fun inlineNoticeMessage(state: HomeState): String? {
    return when {
        state.error is HomeError.NoInternet && state.isUsingCachedData -> stringResource(R.string.notice_offline_cached)
        state.error is HomeError.PermissionDenied && state.isUsingCachedData -> stringResource(R.string.notice_permission_cached)
        state.error is HomeError.LocationDisabled && state.isUsingCachedData -> stringResource(R.string.notice_location_cached)
        state.error != null && state.isUsingCachedData -> stringResource(R.string.notice_showing_cached)
        state.isUsingCachedData -> stringResource(R.string.notice_cached_updating)
        else -> null
    }
}