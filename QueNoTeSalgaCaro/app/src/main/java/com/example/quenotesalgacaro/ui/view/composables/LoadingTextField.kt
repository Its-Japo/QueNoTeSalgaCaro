package com.example.quenotesalgacaro.ui.view.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDropdownTextField(
    selectedWallet: String,
    dates: Array<String>,
    expandedWallet: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onItemSelected: (String) -> Unit,
    uiState: DataUiState<*>,
    width: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    val focusRequester = FocusRequester()


    Box(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expandedWallet,
            onExpandedChange = onExpandedChange,
            modifier = modifier
                .padding(12.dp)
        ) {
            TextField(
                value = selectedWallet,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    if (uiState is DataUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(20.dp)
                        )
                    } else {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWallet)
                    }
               },
                modifier = modifier
                    .menuAnchor()
                    .width(width.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused && expandedWallet) {
                            onExpandedChange(false)
                        }
                    },
                label = {
                    Text(
                        text = label,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                enabled = uiState !is DataUiState.Loading,
            )
            ExposedDropdownMenu(
                expanded = expandedWallet,
                onDismissRequest = expandedWallet::not,
            ) {
                dates.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onItemSelected(item)
                        }
                    )
                }
            }
        }
    }
}