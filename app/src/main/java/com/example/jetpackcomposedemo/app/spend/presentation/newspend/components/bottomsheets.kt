package com.example.jetpackcomposedemo.app.spend.presentation.newspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.data.entities.SpendCategory
import com.example.jetpackcomposedemo.ui.theme.AppColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    categoryData: List<SpendCategory>?,
    showBottomSheet: Boolean,
    onHideBottomSheet: () -> Unit
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
                    }
                )
            }
            HorizontalPager(
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
                                    .padding(8.dp)
                            ) {
                                Text(item.category ?: "", modifier = Modifier.weight(1f))
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
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppColor.Green400)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryBottomSheetPreview() {
    CategoryBottomSheet(listOf(), true) {

    }
}