package com.example.jetpackcomposedemo.app.spend.newspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.AppColor
import com.example.jetpackcomposedemo.ui.theme.getCategoryColor
import java.util.Locale

@Composable
fun CategoryLabel(category: String) {
    val displayText =
        category.split("_").joinToString(" ") { it.replaceFirstChar { c -> c.uppercaseChar() } }
    Text(
        text = displayText,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(getCategoryColor(category))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = AppColor.White,
        style = MaterialTheme.typography.labelSmall
    )
}

@Preview
@Composable
fun CategoryLabelPreview() {
    Column {
        CategoryLabel("transport")
    }
}