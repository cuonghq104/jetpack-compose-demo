package com.example.uikit

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.shimmer

fun Modifier.shimmerAnim(wrapper: ShimmerInstanceWrapper?) = this.composed {
    return@composed wrapper?.instance?.let { sInstance ->
        this.shimmer(sInstance)
    } ?: this.shimmer()
}