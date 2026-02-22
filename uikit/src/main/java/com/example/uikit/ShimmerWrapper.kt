package com.example.uikit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

val LocalShimmerInstance = compositionLocalOf<ShimmerInstanceWrapper?> { null }

data class ShimmerInstanceWrapper(val instance: Shimmer)

@Composable
fun ShimmerWrapper(content: @Composable () -> Unit) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    CompositionLocalProvider(
        LocalShimmerInstance provides ShimmerInstanceWrapper(shimmerInstance)
    ) {
        content()
    }
}