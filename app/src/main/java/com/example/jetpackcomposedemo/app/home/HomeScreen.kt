package com.example.jetpackcomposedemo.app.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposedemo.app.destinationList
import com.example.jetpackcomposedemo.app.spend.SpendScreen
import com.example.jetpackcomposedemo.app.spend.presentation.components.AddOverlayWindows
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.NewSpendScreen


@Composable
fun AppContent(onNewTransactionButtonClick: () -> Unit) {
    var selectedDestination by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    var showAddOverlay by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    destinationList.forEachIndexed { index, destination ->
                        if (index != 1) {
                            NavigationBarItem(
                                selected = selectedDestination == index,
                                onClick = {
                                    navController.navigate(route = destination.route)
                                    selectedDestination = index
                                },
                                icon = {
                                    Icon(
                                        imageVector = destination.icon,
                                        contentDescription = destination.contentDescription
                                    )
                                },
                                label = { Text(destination.label) },
                                modifier = Modifier.weight(2f)
                            )
                        } else {
                            IconButton(
                                onClick = {
                                    showAddOverlay = true
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .clickable {

                                    }
                                    .shadow(elevation = 8.dp)
                                    .weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = destination.contentDescription,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = "spend"
                ) {
                    composable("spend") {
                        SpendScreen()
                    }
                    composable("task") {
                        NewSpendScreen()
                    }
                }
            }
        }

        AddOverlayWindows(
            overlayVisible = showAddOverlay,
            onCloseOverlayClick = {
                showAddOverlay = false
            }, onNewTransactionClick = onNewTransactionButtonClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    AppContent(onNewTransactionButtonClick = {

    })
}
