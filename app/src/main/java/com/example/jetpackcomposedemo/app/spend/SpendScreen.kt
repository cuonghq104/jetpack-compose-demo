package com.example.jetpackcomposedemo.app.spend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposedemo.app.spend.presentation.calendar.SpendCalendarScreen
import com.example.jetpackcomposedemo.app.spend.presentation.overview.SpendOverviewScreen

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
            .padding(16.dp)
    ) {
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = {},
            divider = {}
        ) {
            SingleChoiceSegmentedButtonRow {
                spendTabList.forEachIndexed { index, item ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = spendTabList.size
                        ),
                        onClick = {
                            spendNavController.navigate(if (index == 0) "overview" else "calendar")
                        },
                        selected = currentBackStackEntry?.destination?.route == item.label,
                        label = {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "destination"
                            )
                        },
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