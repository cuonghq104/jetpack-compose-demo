package com.example.jetpackcomposedemo.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object AppColor {
    val Blue = Color(0xFF3F51B5)
    val Green = Color(0xFF4CAF50)
    val Blue50 = Color(0xFFd4bff9)
    val Green50 = Color(0xFFdefabb)
    val Red50 = Color(0xFFffc8b9)
    val Orange50 = Color(0xFFfddeb1)
    val Cyan50 = Color(0xFF96f5f3)
    val Pink50 = Color(0xFFf8b5d0)
}

@OptIn(ExperimentalMaterial3Api::class)
val topBarColor = TopAppBarColors(
    containerColor = AppColor.Blue,
    navigationIconContentColor = Color.White,
    titleContentColor = Color.White,
    actionIconContentColor = Color.White,
    scrolledContainerColor = Color.White
)