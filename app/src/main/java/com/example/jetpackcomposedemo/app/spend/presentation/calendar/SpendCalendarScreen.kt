package com.example.jetpackcomposedemo.app.spend.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun SpendCalendarScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {

    }
}

@Preview(showBackground = true)
@Composable
fun SpendCalendarScreenPreview() {
    val navController = rememberNavController()
    SpendCalendarScreen(navController)
}