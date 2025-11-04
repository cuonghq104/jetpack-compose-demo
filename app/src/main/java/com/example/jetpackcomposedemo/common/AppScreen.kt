package com.example.jetpackcomposedemo.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    title: String,
    content: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppTopBar(title = title)
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.padding(start = 4.dp)
            )
        },
        actions = {

        },
        colors =
            TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                scrolledContainerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            )
    )
}

@Preview
@Composable
fun AppTopBarPreview() {
    AppTopBar(title = "Demo")
}