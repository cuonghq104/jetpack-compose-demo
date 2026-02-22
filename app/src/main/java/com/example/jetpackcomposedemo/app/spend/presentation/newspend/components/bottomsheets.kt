package com.example.jetpackcomposedemo.app.spend.presentation.newspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.data.entities.SpendCategory
import com.example.jetpackcomposedemo.ui.theme.AppColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    categoryData: List<SpendCategory>?,
    showBottomSheet: Boolean,
    detailTabEnable: Boolean,
    onHideBottomSheet: () -> Unit,
    onCategorySelect: (SpendCategory) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var pagerState = rememberPagerState {
        2
    }
    val scope = rememberCoroutineScope()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onHideBottomSheet()
            },
            sheetState = sheetState,
            contentColor = AppColor.Blue900
        ) {
            SecondaryTabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    text = {
                        Text(
                            text = "Category",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    text = {
                        Text(
                            text = "Detail",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    enabled = detailTabEnable
                )
            }
            HorizontalPager(
                userScrollEnabled = detailTabEnable,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) { index ->
                if (index == 0) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(categoryData ?: listOf()) { item ->
                            Row(
                                modifier = Modifier
                                    .background(
                                        when (item.type) {
                                            1 -> AppColor.Red50
                                            2 -> AppColor.Green50
                                            3 -> AppColor.Yellow50
                                            4 -> AppColor.Orange50
                                            else -> AppColor.Gray400
                                        }
                                    )
                                    .fillMaxWidth()
                                    .clickable {
                                        onCategorySelect(item)
                                        scope.launch {
                                            pagerState.animateScrollToPage(1)
                                        }
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    item.category ?: "",
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "Arrow Right",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                } else {
                    CategoryDetailTab(
                        listOf(
                            "Breakfast",
                            "Lunch",
                            "Dinner",
                            "Cafe",
                            "Gasoline",
                            "Electric Billing",
                            "Internet Billing"
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryDetailTab(detailResult: List<String>) {
    var list by remember { mutableStateOf(listOf("") + detailResult) }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.White)
    ) {
        Surface(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_category_food_drink),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Arrow Right",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    "Food & drink",
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onValueChange = {
                searchQuery = it
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.labelLarge,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Arrow Right",
                    modifier = Modifier.size(20.dp)
                )
            },
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            items(list) { item ->
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item == "") {
                        Icon(
                            imageVector = if (searchQuery == "") Icons.Outlined.Info else Icons.Outlined.AddCircle,
                            tint = AppColor.Gray500,
                            contentDescription = "Empty icon",
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = if (searchQuery == "") "No description" else "Description: \"${searchQuery}\"",
                            color = AppColor.Gray500,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    } else {
                        Text(text = item, modifier = Modifier.weight(1f))
                    }
                    if (item != "") {
                        Text(
                            "Select",
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(2.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryBottomSheetPreview() {
    CategoryDetailTab(
        listOf(
            "Breakfast",
            "Lunch",
            "Dinner",
            "Cafe",
        )
    )
//    CategoryBottomSheet(listOf(), true, onHideBottomSheet = {
//
//    }) {
//
//    }
}