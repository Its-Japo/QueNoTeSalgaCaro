package com.example.quenotesalgacaro.ui.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FundsInfoScreen() {
    Column (
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(10.dp, 60.dp)
    ) {
        Text(text = "FundsInfoScreen")
    }
}