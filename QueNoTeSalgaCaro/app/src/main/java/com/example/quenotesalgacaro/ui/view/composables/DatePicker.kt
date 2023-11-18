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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@Composable
fun DatePickerCustom(
    onDateSelected: (Int, Int) -> Unit
){
    var showDialog by remember { mutableStateOf(false) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

    Button(onClick = { showDialog = true }) {
        Text("Select Month and Year")
    }

    if (showDialog) {
        DatePickerDialogCustom(
            initialYear = currentYear,
            initialMonth = currentMonth,
            onMonthYearSelected = { year, month ->
                onDateSelected(year, month)
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
}


@Composable
fun DatePickerDialogCustom(
    initialYear: Int,
    initialMonth: Int,
    onMonthYearSelected: (Int, Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                var selectedYear by remember { mutableStateOf(initialYear) }
                Text(text = "Select Year", style = MaterialTheme.typography.h6)
                Slider(
                    value = (selectedYear - currentYear).toFloat(),
                    onValueChange = { selectedYear = (it.toInt() + currentYear) },
                    valueRange = 0f..(currentYear + 10 - currentYear).toFloat(),
                    steps = 9
                )
                Text(text = "$selectedYear")

                Spacer(modifier = Modifier.height(16.dp))

                val months = listOf(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                )
                var selectedMonth by remember { mutableStateOf(initialMonth) }
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
                            Text(
                                text = month,
                                style = if (index == selectedMonth) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onMonthYearSelected(selectedYear, selectedMonth)
                        onDismissRequest()
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}