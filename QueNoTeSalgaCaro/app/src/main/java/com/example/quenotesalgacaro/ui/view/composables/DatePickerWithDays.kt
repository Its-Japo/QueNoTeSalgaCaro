package com.example.quenotesalgacaro.ui.view.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.quenotesalgacaro.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = stringResource(id = R.string.Ok))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.Cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            dateFormatter = remember {
                DatePickerDefaults.dateFormatter()
            }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC-6")
    return dateFormat.format(millis)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogD(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    var date by remember {
        mutableStateOf(context.getString(R.string.SelectDate))
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Box(contentAlignment = Alignment.Center) {
        Button(onClick = { showDatePicker = true }) {
            Text(text = date)
        }
    }

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = {
                date = it
                onDateSelected(it)
             },
            onDismiss = { showDatePicker = false }
        )
    }
}