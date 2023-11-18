package com.example.quenotesalgacaro.ui.view.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersistentPlaceholderTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    width: Int,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = false,
            modifier = modifier
                .width(width.dp)
                .padding(12.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)

        )

        if (!isFocused && value.text.isEmpty()) {
            Text(
                text = placeholder,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            )
        }

        Text(
            text = label,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 4.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.high)
        )
    }
}