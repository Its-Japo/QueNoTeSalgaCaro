package com.example.quenotesalgacaro.ui.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    error: Throwable,
    paddingValues: PaddingValues = PaddingValues(10.dp, 10.dp, 10.dp, 10.dp)
) {

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(15.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(
                color = Color(166, 13, 29, 100),
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 1.dp,
                color = Color(166, 13, 29, 255),
                shape = MaterialTheme.shapes.medium
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ha ocurrido un error",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(10.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = error.message ?: "Error desconocido",
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(10.dp),
            textAlign = TextAlign.Center,
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
        )
    }

}