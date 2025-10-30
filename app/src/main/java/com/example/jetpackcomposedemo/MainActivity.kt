package com.example.jetpackcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposedemo.ui.theme.AppColor
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.example.jetpackcomposedemo.ui.theme.topBarColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainActivityContent()
        }
    }
}

@Composable
fun MainActivityContent() {
    val navController = rememberNavController()
    JetpackComposeDemoTheme {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                ListScreenContent(navController)
            }
            composable("detail") {
                DetailContent(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenContent(navController: NavController?, viewModel: MainViewModel = MainViewModel()) {
    var galleryMode by remember { mutableStateOf(false) }
    var contactList by viewModel.contactList.observeAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController?.navigate("detail")
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Access Alarm",
                    tint = Color.Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("") },
                colors = topBarColor,
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            enabled = !galleryMode,
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                galleryMode = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountBox,
                                contentDescription = "Access Alarm",
                                tint = if (galleryMode) Color.Yellow else Color.LightGray,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                        IconButton(
                            enabled = galleryMode,
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                galleryMode = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Access Alarm",
                                tint = if (!galleryMode) Color.Yellow else Color.LightGray,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(contactList) {  }
        }
    }
}

@Composable
fun DetailContent(navController: NavController?) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(AppColor.Green)
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ListScreenContent(null)
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    DetailContent(null)
}