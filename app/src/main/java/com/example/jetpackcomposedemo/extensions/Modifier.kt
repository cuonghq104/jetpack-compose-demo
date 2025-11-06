package com.example.jetpackcomposedemo.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = this.then(
    Modifier.drawBehind {
        val stroke = strokeWidth.toPx()
        val y = size.height - stroke / 2
        drawLine(color, Offset(0f, y), Offset(size.width, y), stroke)
    }
)
