package com.example.jetpackcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    val contactList by viewModel.contactList.observeAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController?.navigate("detail")
//                    viewModel.addContact(
//                        Contact(
//                            "1",
//                            "Demo",
//                            "0123456789",
//                            cardColor = Color.Yellow
//                        )
//                    )
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
        ) {
            LazyColumn() {
                items(contactList ?: emptyList()) { item ->
                    ContactCard(item)
                }
            }
        }
    }
}

@Composable
fun ContactCard(contact: Contact) {
    var showAction by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Surface(
            onClick = {
                showAction = !showAction
            },
            color = contact.cardColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://ui-avatars.com/api/?name=${contact.name}")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp)
                            .clip(CircleShape),
                    )

                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .wrapContentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Access Alarm",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(text = contact.name, style = MaterialTheme.typography.labelLarge)
                Text(text = contact.contactNumber)
            }
        }
        if (showAction) {
            Row(modifier = Modifier.background(contact.cardColor)) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .background(contact.cardColor)
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Access Alarm",
                        tint = Color.Green,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "Send message", modifier = Modifier.padding(start = 8.dp))
                }
                VerticalDivider(
                    modifier = Modifier
                        .height(48.dp)
                        .width(1.dp),
                    color = Color.LightGray
                )
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "Call",
                        tint = Color.Green,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "Call", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

@Composable
fun DetailContent(navController: NavController?) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf<Color>(AppColor.Blue50) }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(AppColor.Green)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppInputField(
                label = "Name",
                inputValue = name,
                onInputChange = { newVal ->
                    name = newVal
                }
            )

            AppInputField(
                label = "Phone number",
                inputValue = phoneNumber,
                onInputChange = { newVal ->
                    phoneNumber = newVal
                }
            )

            Column {
                Text(
                    text = "Color",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Blue
                )
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    for (groupColor in listOf(
                        AppColor.Blue50, AppColor.Green50, AppColor.Pink50,
                        AppColor.Cyan50, AppColor.Orange50, AppColor.Red50
                    ).chunked(3)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            for (color in groupColor) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(color)
                                        .clickable {
                                            selectedColor = color
                                        }
                                        .border(
                                            width = if (selectedColor == color) 5.dp else 0.dp,
                                            color = Color.Blue
                                        )
                                )
                            }
                        }

                    }
                }

            }
        }
    }
}

@Composable
fun AppInputField(label: String, inputValue: String, onInputChange: (String) -> Unit) {
    Column {
        Text(text = label, style = MaterialTheme.typography.titleMedium, color = Color.Blue)
        TextField(
            value = inputValue,
            onValueChange = onInputChange,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
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