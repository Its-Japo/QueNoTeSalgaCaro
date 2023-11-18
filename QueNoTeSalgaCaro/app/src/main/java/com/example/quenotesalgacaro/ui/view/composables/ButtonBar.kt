package com.example.quenotesalgacaro.ui.view.composables

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonBar(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    text: String,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Button (
        content = {
            Row (
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = tint,
                        modifier = modifier.weight(1f),
                    )
                }
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    modifier = modifier.weight(7f)
                        .padding(10.dp, 10.dp, 0.dp, 10.dp)
                )
            }
        },
        onClick = onClick,
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    )
    Spacer(modifier = modifier.height(1.dp)
        .padding(20.dp, 0.dp, 20.dp, 0.dp)
        .background(Color.LightGray)
        .fillMaxWidth()
    )

}

@Composable
 @Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun GreetingPreview() {
    ButtonBar(text= "Hola", onClick = { /*TODO*/ })
}
