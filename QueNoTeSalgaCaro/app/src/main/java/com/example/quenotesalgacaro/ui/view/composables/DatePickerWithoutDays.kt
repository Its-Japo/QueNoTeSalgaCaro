package com.example.quenotesalgacaro.ui.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Calendar
import com.example.quenotesalgacaro.R

@Composable
fun DatePickerWithoutDays(
    onDateSelected: (Int, String) -> Unit
){
    var showDialog by remember { mutableStateOf(false) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

    val months = listOf(
        "jan", "feb", "mar", "apr", "may", "jun",
        "jul", "aug", "sep", "oct", "nov", "dec"
    )

    val date = remember {
        mutableStateOf("${months[currentMonth]}.-$currentYear")
    }

    Button(onClick = { showDialog = true }) {
        Text(text = date.value)
    }

    if (showDialog) {
        DatePickerWithoutDaysDialog(
            initialYear = currentYear,
            initialMonth = currentMonth,
            onMonthYearSelected = { year, month ->
                date.value = "$month.-$year"
                onDateSelected(year, month)
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
}


@Composable
fun DatePickerWithoutDaysDialog(
    initialYear: Int,
    initialMonth: Int,
    onMonthYearSelected: (Int, String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                var selectedYear by remember { mutableIntStateOf(initialYear) }
                var selectedMonth by remember { mutableIntStateOf(initialMonth) }
                Text(text = stringResource(id = R.string.SelectYear), style = MaterialTheme.typography.headlineSmall)
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            selectedYear -= 1
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "-",
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Text(
                        text = "$selectedYear",
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(3f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    IconButton(
                        onClick = {
                            selectedYear += 1
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                    ) {
                        Text(
                            text = "+",
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                val months = listOf(
                    "jan", "feb", "mar", "apr", "may", "jun",
                    "jul", "aug", "sep", "oct", "nov", "dec"
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(months) { index, month ->
                        TextButton(
                            onClick = { selectedMonth = index },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            if (index == selectedMonth) {
                                Surface(
                                    shape = MaterialTheme.shapes.large,
                                    color = MaterialTheme.colorScheme.primary,
                                ) {
                                    Text(
                                        text = month,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            } else {
                                Text(
                                    text = month,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = stringResource(id = R.string.Cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onMonthYearSelected(selectedYear, months[selectedMonth])
                        onDismissRequest()
                    }) {
                        Text(text = stringResource(id = R.string.Ok))
                    }
                }
            }
        }
    }
}