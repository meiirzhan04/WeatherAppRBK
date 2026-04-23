package edu.learn.weatherapprbk.feature.home.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.learn.resources.components.LoadingScreen
import edu.learn.resources.theme.WeatherAppRBKTheme
import edu.learn.weatherapprbk.R
import edu.learn.weatherapprbk.core.extensions.hasLocationPermission
import edu.learn.weatherapprbk.core.extensions.isLocationEnabled
import edu.learn.weatherapprbk.core.extensions.openLocationSettings
import edu.learn.weatherapprbk.feature.home.presentation.components.blocks.ShowTimeWeatherBox
import edu.learn.weatherapprbk.feature.home.presentation.components.blocks.ShowWeatherTenDayBox
import edu.learn.weatherapprbk.feature.home.presentation.components.blocks.WeatherCollapsingHeader
import edu.learn.weatherapprbk.feature.home.presentation.components.blocks.WeatherHighlightsGrid
import edu.learn.weatherapprbk.feature.home.presentation.components.errors.inlineNoticeMessage
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var skipFirstResume by remember { mutableStateOf(true) }
    var hasRequestedLocationPermission by rememberSaveable { mutableStateOf(false) }
    val locationPermissions = remember {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        viewModel.onIntent(
            HomeIntent.PermissionResult(
                granted = context.hasLocationPermission(),
                isLocationEnabled = context.isLocationEnabled()
            )
        )
    }
    val requestLocationPermission = {
        hasRequestedLocationPermission = true
        permissionLauncher.launch(locationPermissions)
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(
            HomeIntent.Initialize(
                hasLocationPermission = context.hasLocationPermission(),
                isLocationEnabled = context.isLocationEnabled()
            )
        )
    }
    LaunchedEffect(
        state.isSystemStateKnown,
        state.hasLocationPermission,
        state.isUsingCachedData,
        state.weather == null
    ) {
        if (
            state.isSystemStateKnown &&
            !context.hasLocationPermission() &&
            !hasRequestedLocationPermission &&
            state.weather == null &&
            !state.isUsingCachedData
        ) {
            requestLocationPermission()
        }
    }

    DisposableEffect(lifecycleOwner, context) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (skipFirstResume) {
                    skipFirstResume = false
                    return@LifecycleEventObserver
                }
                viewModel.onIntent(
                    HomeIntent.SystemStatusChanged(
                        hasLocationPermission = context.hasLocationPermission(),
                        isLocationEnabled = context.isLocationEnabled()
                    )
                )
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LoadingScreen(isLoading = state.isLoading && state.weather == null) {
        val statusBackgroundRes = state.lastKnownWeather?.let(WeatherVisualResolver::resolveBackground) ?: R.drawable.day
        when {
            state.weather != null -> HomeScreenContent(state = state, onRefresh = { viewModel.onIntent(HomeIntent.Refresh) })
            !state.isSystemStateKnown || state.isLoading -> {
                HomeStatusScreen(
                    title = stringResource(R.string.loading_weather_title),
                    description = stringResource(R.string.loading_weather_description),
                    backgroundRes = statusBackgroundRes
                )
            }

            state.error is HomeError.LocationDisabled -> {
                HomeStatusScreen(
                    title = stringResource(R.string.error_location_disabled_title),
                    description = stringResource(R.string.error_location_disabled_description),
                    primaryActionLabel = stringResource(R.string.open_location_settings),
                    onPrimaryAction = { context.openLocationSettings() },
                    backgroundRes = statusBackgroundRes
                )
            }

            state.error is HomeError.NoInternet -> {
                HomeStatusScreen(
                    title = stringResource(R.string.error_no_internet_title),
                    description = stringResource(R.string.error_no_internet_description),
                    primaryActionLabel = stringResource(R.string.retry),
                    onPrimaryAction = { viewModel.onIntent(HomeIntent.Retry) },
                    backgroundRes = statusBackgroundRes
                )
            }

            state.error is HomeError.Server -> {
                val serverError = state.error as HomeError.Server
                HomeStatusScreen(
                    title = stringResource(R.string.error_server_title),
                    description = stringResource(
                        R.string.error_server_description,
                        serverError.code ?: 0
                    ),
                    primaryActionLabel = stringResource(R.string.retry),
                    onPrimaryAction = { viewModel.onIntent(HomeIntent.Retry) },
                    backgroundRes = statusBackgroundRes
                )
            }

            else -> {
                HomeStatusScreen(
                    title = stringResource(R.string.loading_weather_title),
                    description = stringResource(R.string.loading_weather_description),
                    backgroundRes = statusBackgroundRes
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weather = state.weather ?: return
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val expandedHeaderHeight = 300.dp
    val collapsedHeaderHeight = 110.dp

    val collapseRangePx = remember(density) { with(density) { (expandedHeaderHeight - collapsedHeaderHeight).toPx() } }
    val scrollOffsetPx by remember(listState) {
        derivedStateOf {
            when {
                listState.firstVisibleItemIndex > 0 -> collapseRangePx
                else -> listState.firstVisibleItemScrollOffset.toFloat().coerceAtMost(collapseRangePx)
            }
        }
    }

    val collapseProgress by remember(scrollOffsetPx, collapseRangePx) {
        derivedStateOf {
            if (collapseRangePx == 0f) 0f
            else (scrollOffsetPx / collapseRangePx).coerceIn(0f, 1f)
        }
    }

    val currentHeaderHeight = lerp(
        start = expandedHeaderHeight,
        stop = collapsedHeaderHeight,
        fraction = collapseProgress
    )

    Box(modifier = modifier.fillMaxSize()) {
        Crossfade(targetState = weather) { currentWeather ->
            Image(
                painter = painterResource(WeatherVisualResolver.resolveBackground(currentWeather)),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.16f)))
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(expandedHeaderHeight))
                    inlineNoticeMessage(state)?.let { message ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.Black.copy(alpha = 0.20f))
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = message,
                                style = WeatherAppRBKTheme.typography.weight400Size14LineHeight20,
                                color = WeatherAppRBKTheme.colors.textPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    ShowTimeWeatherBox(
                        infoText = stringResource(R.string.hourly_weather_summary, weather.description),
                        hourlyForecast = state.hourlyForecast
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShowWeatherTenDayBox(forecast = state.forecast)
                    Spacer(modifier = Modifier.height(8.dp))
                    state.weatherDetails?.let { details ->
                        WeatherHighlightsGrid(
                            weather = weather,
                            details = details,
                            forecast = state.forecast,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

        WeatherCollapsingHeader(
            title = stringResource(R.string.weather_title),
            cityName = weather.cityName,
            degree = stringResource(R.string.temperature_degree, weather.temperature.roundToInt()),
            conditionText = WeatherVisualResolver.resolveConditionText(description = weather.description, fallbackText = stringResource(R.string.weather_condition_default)),
            max = stringResource(R.string.temperature_degree, weather.tempMax.roundToInt()),
            min = stringResource(R.string.temperature_degree, weather.tempMin.roundToInt()),
            progress = collapseProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(currentHeaderHeight)
                .align(Alignment.TopCenter)
                .zIndex(2f)
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
        )
    }
}

@Composable
private fun HomeStatusScreen(
    title: String,
    description: String,
    primaryActionLabel: String? = null,
    onPrimaryAction: (() -> Unit)? = null,
    backgroundRes: Int = R.drawable.day,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.24f))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = WeatherAppRBKTheme.dimensions.mediumMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White.copy(alpha = 0.12f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = WeatherAppRBKTheme.dimensions.mediumMedium,
                            vertical = WeatherAppRBKTheme.dimensions.mediumLarge
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = WeatherAppRBKTheme.typography.weight600Size34LineHeight41,
                        color = WeatherAppRBKTheme.colors.textPrimary
                    )
                    Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.extraMedium))
                    Text(
                        text = description,
                        style = WeatherAppRBKTheme.typography.weight400Size14LineHeight20,
                        color = WeatherAppRBKTheme.colors.textSecondary
                    )
                    if (primaryActionLabel != null && onPrimaryAction != null) {
                        Spacer(modifier = Modifier.height(WeatherAppRBKTheme.dimensions.mediumLarge))
                        Button(
                            onClick = onPrimaryAction,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.20f),
                                contentColor = WeatherAppRBKTheme.colors.textPrimary
                            )
                        ) {
                            Text(
                                text = primaryActionLabel,
                                style = WeatherAppRBKTheme.typography.weight500Size16LineHeight21,
                                color = WeatherAppRBKTheme.colors.textPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}