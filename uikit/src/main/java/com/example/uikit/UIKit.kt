package com.example.uikit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Sample UIKit component to demonstrate the module structure.
 * Replace this with your actual UI components.
 */
@Composable
fun UIKitSample() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("UIKit Module")
    }
}

@Preview(showBackground = true)
@Composable
fun UIKitSamplePreview() {
    UIKitSample()
}

