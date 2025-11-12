package com.example.jetpackcomposedemo.app.spend.newspend.components

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposedemo.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(showBottomSheet: Boolean, onHideBottomSheet: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onHideBottomSheet()
            },
            sheetState = sheetState,
            contentColor = AppColor.Blue900
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryBottomSheetPreview() {
    CategoryBottomSheet(true) {

    }
}