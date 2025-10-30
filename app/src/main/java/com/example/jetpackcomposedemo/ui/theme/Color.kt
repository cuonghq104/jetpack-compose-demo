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
}

@OptIn(ExperimentalMaterial3Api::class)
val topBarColor = TopAppBarColors(
    containerColor = AppColor.Blue,
    navigationIconContentColor = Color.White,
    titleContentColor = Color.White,
    actionIconContentColor = Color.White,
    scrolledContainerColor = Color.White
)