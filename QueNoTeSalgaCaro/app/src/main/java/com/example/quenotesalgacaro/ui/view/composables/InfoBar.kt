package com.example.quenotesalgacaro.ui.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quenotesalgacaro.R

@Composable
fun InfoBar(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(text = text,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier.weight(3f)
                .padding(12.dp, 0.dp, 0.dp, 0.dp),

        )


        IconButton(
            onClick = onClick,
            modifier = modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.option),
                contentDescription = null,
                modifier = modifier
                    .scale(0.75f),
            )
        }
    }

    Spacer(modifier = modifier.height(1.dp)
        .padding(20.dp, 0.dp, 20.dp, 0.dp)
        .background(Color.LightGray)
        .fillMaxWidth()
    )

}
