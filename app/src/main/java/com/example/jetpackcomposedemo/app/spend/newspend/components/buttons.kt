package com.example.jetpackcomposedemo.app.spend.newspend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.AppColor

enum class TagButtonType {
    ADD,
    TAG
}

@Composable
fun TagButton(
    tagButtonType: TagButtonType = TagButtonType.ADD,
    text: String? = "",
    buttonClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (tagButtonType == TagButtonType.ADD) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary)
            .clickable(enabled = tagButtonType == TagButtonType.ADD, onClick = buttonClick)
            .padding(start = 4.dp, end = 4.dp),
    ) {
        Text(
            text = text ?: "",
            style = MaterialTheme.typography.labelSmall,
            color = AppColor.White,
            modifier = Modifier
                .height(24.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(horizontal = 4.dp),
            fontWeight = FontWeight.SemiBold
        )
        Icon(
            imageVector = if (tagButtonType == TagButtonType.ADD) Icons.Outlined.Add else Icons.Outlined.Close,
            tint = AppColor.White,
            contentDescription = "Arrow Right",
            modifier = Modifier
                .size(16.dp)
                .clickable(enabled = tagButtonType == TagButtonType.TAG, onClick = buttonClick)
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    Column {
        TagButton(TagButtonType.ADD, "Add tag") {

        }
        TagButton(TagButtonType.TAG, "Fast food") {

        }
    }
}