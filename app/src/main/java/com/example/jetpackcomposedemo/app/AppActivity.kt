package com.example.jetpackcomposedemo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposedemo.app.home.AppContent
import com.example.jetpackcomposedemo.app.spend.newspend.NewSpendScreen
import com.example.jetpackcomposedemo.app.ui.theme.JetpackComposeDemoTheme

data class Destination(
    val icon: ImageVector,
    val route: String,
    val contentDescription: String,
    val label: String
)

val destinationList = listOf(
    Destination(Icons.Filled.Person, "spend", "Spend", "Spend"),
    Destination(Icons.Filled.Create, "add", "add", "add"),
    Destination(Icons.Filled.AccountBox, "task", "Task", "Task")
)

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeDemoTheme {
                HomeScreen()
            }
        }
    }
}


@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            AppContent {
                navController.navigate("newSpend")
            }
        }
        composable("newSpend") {
            NewSpendScreen()
        }
    }
}