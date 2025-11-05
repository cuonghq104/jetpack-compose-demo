package com.example.jetpackcomposedemo.app.spend.newspend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetpackcomposedemo.app.spend.newspend.components.SpendSection
import com.example.jetpackcomposedemo.app.spend.newspend.models.Contact
import com.example.jetpackcomposedemo.app.spend.newspend.models.Transaction
import com.example.jetpackcomposedemo.app.spend.spendTabList
import com.example.jetpackcomposedemo.common.AppScreen
import com.example.jetpackcomposedemo.ui.theme.AppColor

data class TransactionType(
    val id: String,
    val title: String,
    val color: Color,
    val inactiveColor: Color
)

val transactionTypes = listOf(
    TransactionType("in", "Money in", AppColor.Blue, AppColor.Blue50),
    TransactionType("out", "Money out", AppColor.Green, AppColor.Green50)
)

@Composable
fun TextFieldWithDropdown(
    modifier: Modifier = Modifier,
    dropDownModifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<String>,
    label: String = ""
) {
    Box(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            placeholder = {
                Text(text = label)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "destination"
                )
            }
        )
        DropdownMenu(
            expanded = true,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest,
            modifier = dropDownModifier,
            containerColor = Color.Transparent,
            shadowElevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(Color.White),

                ) {
                list.forEachIndexed { index, text ->
                    Column(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Cô bán bánh tráng",
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "20K",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.W600
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Transport",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(AppColor.Green)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                            Text(
                                text = "Đổ xăng",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                        if (index != list.size - 1) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Color.LightGray,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun NewSpendScreen() {
    var selectedTransactionType by remember { mutableStateOf("in") }
    var selectedPrimaryColor by remember { mutableStateOf(AppColor.Blue) }


    var formState by remember {
        mutableStateOf<Transaction>(
            Transaction(
                contact = Contact(name = ""),
            )
        )
    }
    val localColorScheme = MaterialTheme.colorScheme.copy(
        primary = selectedPrimaryColor,
        onPrimary = Color.White
    )


    MaterialTheme(colorScheme = localColorScheme) {
        AppScreen(title = "Spend") { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    transactionTypes.forEachIndexed { index, item ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = spendTabList.size
                            ),
                            onClick = {
                                selectedTransactionType = item.id
                                selectedPrimaryColor = item.color
                            },
                            selected = selectedTransactionType == item.id,
                            label = {
                                Text(text = item.title, color = Color.White)
                            },
                            icon = {

                            },
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = item.color,
                                inactiveContainerColor = Color.LightGray
                            )
                        )
                    }
                }

                SpendSection(
                    title = if (selectedTransactionType == "in") "Sender" else "Receiver",
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    TextFieldWithDropdown(
                        value = formState.contact.name,
                        onValueChange = { value ->
                            formState = formState.copy(
                                contact = formState.contact.copy(
                                    name = value
                                )
                            )
                        },
                        onDismissRequest = {

                        },
                        dropDownExpanded = true,
                        list = listOf("aaa", "bbb", "ccc"),
                        label = "Name",
                        modifier = Modifier
                            .fillMaxWidth(),
                        dropDownModifier = Modifier
                            .fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = {

                            },
                            modifier = Modifier
                                .size(24.dp)      // visual size of the box
                                .padding(0.dp)

                        )

                        Text(
                            text = "Advance options",
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.labelMedium
                        )

                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewSpendScreenPreview() {
    NewSpendScreen()
}