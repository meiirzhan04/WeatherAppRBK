package edu.learn.weatherapprbk.core.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import edu.learn.resources.theme.WeatherAppRBKDimensions
import kotlinx.coroutines.launch

private val defaultDimensions = WeatherAppRBKDimensions()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    isVisible: MutableState<Boolean>,
    onVisibilityChanged: (Boolean) -> Unit = {},
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = defaultDimensions.zero,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    content: @Composable ColumnScope.() -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isVisible.value) {
        if (isVisible.value) sheetState.show() else sheetState.hide()
    }

    LaunchedEffect(sheetState.isVisible) {
        isVisible.value = sheetState.isVisible
        onVisibilityChanged(sheetState.isVisible)
    }

    if (sheetState.isVisible || isVisible.value) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }
                onVisibilityChanged(false)
                onDismiss.invoke()
            },
            modifier = modifier.systemBarsPadding(),
            sheetState = sheetState,
            sheetMaxWidth = sheetMaxWidth,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            scrimColor = scrimColor,
            dragHandle = dragHandle,
            contentWindowInsets = contentWindowInsets,
            properties = properties,
            content = { content.invoke(this) }
        )
    }
}
