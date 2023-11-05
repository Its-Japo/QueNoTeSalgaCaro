package com.example.quenotesalgacaro.ui.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ButtonBar(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    text: String,
    onClick: () -> Unit
) {
    Button (
        content = {
            Row (
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
            ) {
                if (icon != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = text,

                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        onClick = onClick,
        modifier = modifier.background(Color.White),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    )
    Spacer(modifier = modifier.height(1.dp)
        .padding(20.dp, 0.dp, 20.dp, 0.dp)
        .background(Color.LightGray)
        .fillMaxWidth()
    )

}