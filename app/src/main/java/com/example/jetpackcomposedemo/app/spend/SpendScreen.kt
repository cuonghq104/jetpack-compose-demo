package com.example.jetpackcomposedemo.app.spend

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposedemo.DetailContent
import com.example.jetpackcomposedemo.ListScreenContent

data class SpendTab(val label: String)

val spendTabList = listOf(
    SpendTab("overview"),
    SpendTab("calendar")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendScreen() {
    val spendNavController = rememberNavController()
    val currentBackStackEntry by spendNavController.currentBackStackEntryAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            SingleChoiceSegmentedButtonRow {
                spendTabList.forEachIndexed { index, item ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = spendTabList.size
                        ),
                        onClick = {
                            Log.d("TAG", "SpendScreen: ${currentBackStackEntry?.destination?.route}")
                            Log.d("TAG", "SpendScreen: ${item.label}")
                            spendNavController.navigate(if (index == 0) "overview" else "calendar")
                        },
                        selected = currentBackStackEntry?.destination?.route == item.label,
                        label = { Text(item.label) }
                    )
                }
            }
        }
        NavHost(navController = spendNavController, startDestination = "overview") {
            composable("overview") {
                SpendOverviewScreen(spendNavController)
            }
            composable("calendar") {
                SpendCalendarScreen(spendNavController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpendScreenPreview() {
    SpendScreen()
}