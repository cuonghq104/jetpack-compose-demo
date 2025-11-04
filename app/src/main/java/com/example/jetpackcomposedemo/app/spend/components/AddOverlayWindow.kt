package com.example.jetpackcomposedemo.app.spend.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.ui.theme.AppColor

@Preview(showBackground = true)
@Composable
fun AddOverlayWindowsPreview() {
    AddOverlayWindows(true, onCloseOverlayClick = {

    })
}

@Composable
fun AddOverlayWindows(
    overlayVisible: Boolean,
    onCloseOverlayClick: () -> Unit
) {
    val transition = updateTransition(targetState = overlayVisible, label = "overlayTransition")
    val scale by transition.animateFloat(
        transitionSpec = {
            if (targetState) tween(400, easing = FastOutSlowInEasing)
            else tween(300, easing = LinearOutSlowInEasing)
        },
        label = "scale"
    ) { if (it) 1f else 0f }
    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) },
        label = "alpha"
    ) { if (it) 0.6f else 0f }

    if (transition.currentState || transition.targetState) {
        Box {
            Box(
                modifier = Modifier
                    .padding(WindowInsets.systemBars.asPaddingValues())
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = alpha))
                    .padding(16.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin(0.5f, 1f) // bottomCenter
                    }
                    .pointerInput(Unit) {

                    }
            ) {
                IconButton(
                    onClick = onCloseOverlayClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(
                            Color.White
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close",
                        tint = Color.Black
                    )
                }

                IconButton(
                    onClick = {
                        // TODO:
                    },
                    modifier = Modifier
                        .padding(bottom = LocalConfiguration.current.screenWidthDp.dp / 5)
                        .size(LocalConfiguration.current.screenWidthDp.dp / 5)
                        .align(Alignment.BottomCenter)
                        .clip(CircleShape)
                        .background(
                            AppColor.Blue
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_cloud_sync_24),
                        contentDescription = "icon sync",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                IconButton(
                    onClick = {
                        // TODO:
                    },
                    modifier = Modifier
                        .padding(start = LocalConfiguration.current.screenWidthDp.dp / 10)
                        .size(LocalConfiguration.current.screenWidthDp.dp / 5)
                        .align(Alignment.BottomStart)
                        .clip(CircleShape)
                        .background(
                            AppColor.Red
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_sync_alt_24),
                        contentDescription = "close",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                IconButton(
                    onClick = {
                        // TODO:
                    },
                    modifier = Modifier
                        .padding(end = LocalConfiguration.current.screenWidthDp.dp / 10)
                        .size(LocalConfiguration.current.screenWidthDp.dp / 5)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(
                            AppColor.Green
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_task_24),
                        contentDescription = "icon task",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}