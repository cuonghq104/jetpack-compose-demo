package com.example.jetpackcomposedemo.app.spend.presentation.newspend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.app.spend.data.repository.SpendCategoryRepositoryImpl
import com.example.jetpackcomposedemo.app.spend.data.source.local.CategoryLocalDataSource
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.components.CategoryLabel
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.components.SpendSection
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.components.TagButton
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.components.TagButtonType
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.components.TextFieldWithDropdown
import com.example.jetpackcomposedemo.app.spend.domain.models.Contact
import com.example.jetpackcomposedemo.app.spend.domain.models.Transaction
import com.example.jetpackcomposedemo.app.spend.domain.usecases.GetCategoryUseCase
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.components.CategoryBottomSheet
import com.example.jetpackcomposedemo.app.spend.spendTabList
import com.example.jetpackcomposedemo.common.AppScreen
import com.example.jetpackcomposedemo.data.db.SpendDB
import com.example.jetpackcomposedemo.extensions.bottomBorder
import com.example.jetpackcomposedemo.ui.theme.AppColor

data class TransactionType(
    val id: String,
    val title: String,
)

val transactionTypes = listOf(
    TransactionType("in", "Money in"),
    TransactionType("out", "Money out")
)

@Composable
fun NewSpendScreen() {
    val context = LocalContext.current
    val factory = remember {
        NewSpendViewModelFactory(
            GetCategoryUseCase(
                SpendCategoryRepositoryImpl(
                    CategoryLocalDataSource(
                        SpendDB.getInstance(context = context.applicationContext).spendCategoryDao()
                    )
                )
            )
        )
    }
    val viewModel: NewSpendViewModel = viewModel(factory = factory)
    var selectedTransactionType by remember { mutableStateOf("in") }
    var category by remember { mutableStateOf(false) }
    var showCategoryBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        viewModel.getCategoryList()
    }

    var formState by remember {
        mutableStateOf<Transaction>(
            Transaction(
                contact = Contact(name = ""),
            )
        )
    }

    val localColorScheme =
        if (selectedTransactionType == "in")
            MaterialTheme.colorScheme.copy(
                primary = AppColor.Blue900,
                onPrimary = AppColor.Blue50,
                primaryContainer = AppColor.Blue100,
                secondary = AppColor.Green900,
                onSecondary = AppColor.Green50,
                secondaryContainer = AppColor.Blue100
            )
        else MaterialTheme.colorScheme.copy(
            primary = AppColor.Green900,
            onPrimary = AppColor.Green50,
            primaryContainer = AppColor.Green100,
            secondary = AppColor.Blue900,
            onSecondary = AppColor.Blue50,
            secondaryContainer = AppColor.Green100
        )


    MaterialTheme(colorScheme = localColorScheme) {
        AppScreen(title = "Spend") { innerPadding ->
            CategoryBottomSheet(
                viewModel.categoryList.value,
                showCategoryBottomSheet) {
                showCategoryBottomSheet = false
            }
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
                            },
                            selected = selectedTransactionType == item.id,
                            label = {
                                Text(text = item.title, color = Color.White)
                            },
                            icon = {

                            },
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = localColorScheme.primary,
                                inactiveContainerColor = localColorScheme.secondaryContainer
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .bottomBorder(1.dp, AppColor.Gray300)
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Date",
                            modifier = Modifier
                                .weight(1f),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "Today",
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            color = AppColor.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    SpendSection(
                        title = "Amount"
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextField(
                                value = "abc",
                                onValueChange = {

                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(64.dp),
                                singleLine = true,
                                trailingIcon = {
                                    Text(
                                        "000 ₫",
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = AppColor.Gray500
                                    )
                                },
                                textStyle = MaterialTheme.typography.titleLarge,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            )
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .weight(1f)
                                    .height(64.dp),
                                shape = RoundedCornerShape(4.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            showCategoryBottomSheet = true
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_category_food_drink),
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        contentDescription = "Arrow Right",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(2.dp)
                                    )
                                    Text(
                                        "Food & drink",
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        contentDescription = "Arrow Right",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .bottomBorder(1.dp, AppColor.Gray300)
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (!category) {
                                Text(
                                    text = "Select detail category",
                                    modifier = Modifier
                                        .weight(1f),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            } else {
                                CategoryLabel("transport")
                                Text(
                                    text = "Đổ xăng",
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1f),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                tint = Color.Black,
                                contentDescription = "Arrow Right",
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        FlowRow(
                            modifier = Modifier.padding(top = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            listOf("Eat out", "Fast food", "Death", "With friends").map { it ->
                                TagButton(TagButtonType.TAG, it) {

                                }
                            }
                            TagButton(TagButtonType.ADD, "Add tag") {

                            }
                        }
                    }

                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "Add transaction",
                            color = MaterialTheme.colorScheme.onPrimary,
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