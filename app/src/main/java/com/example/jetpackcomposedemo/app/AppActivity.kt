package com.example.jetpackcomposedemo.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackcomposedemo.BaseActivity
import com.example.jetpackcomposedemo.app.home.AppContent
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.NewSpendScreen
import com.example.jetpackcomposedemo.app.ui.theme.JetpackComposeDemoTheme
import com.example.jetpackcomposedemo.data.db.SpendDB
import com.example.uikit.LocalShimmerInstance
import com.example.uikit.ShimmerWrapper
import com.example.uikit.shimmerAnim
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

class AppActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val db = SpendDB.getInstance(applicationContext)
            val dao = db.spendCategoryDao()
            val result = dao.getAll()
            Log.d("TAG", "onCreate: ${result.size}")
        }
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
    ShimmerWrapper {
        val instance = LocalShimmerInstance.current
        Column {
            Row {
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(100.dp)
                        .background(Color.Blue)
                        .shimmerAnim(instance)
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(50.dp)
                        .background(Color.Blue)
                        .shimmerAnim(instance)
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(200.dp)
                        .background(Color.Blue)
                        .shimmerAnim(instance)
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(120.dp)
                        .background(Color.Blue)
                        .shimmerAnim(instance)
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(20.dp)
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .shimmerAnim(instance)
            )
        }
    }
//    NavHost(
//        navController = navController,
//        startDestination = "home"
//    ) {
//        composable("home") {
//            AppContent {
//                navController.navigate("newSpend")
//            }
//        }
//        composable("newSpend") {
//            NewSpendScreen()
//        }
//    }
}