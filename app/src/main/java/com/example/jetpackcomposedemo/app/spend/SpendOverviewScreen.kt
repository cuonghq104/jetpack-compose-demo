package com.example.jetpackcomposedemo.app.spend

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
fun SpendOverviewScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Yellow)) {

    }
}

@Preview(showBackground = true)
@Composable
fun SpendOverviewScreenPreview() {
    val navController = rememberNavController()
    SpendOverviewScreen(navController)
}