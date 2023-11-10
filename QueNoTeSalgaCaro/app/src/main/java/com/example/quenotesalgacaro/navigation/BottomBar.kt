package com.example.quenotesalgacaro.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quenotesalgacaro.R

@Composable
fun BottomBar1(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.height(1.dp)
        .fillMaxWidth()
        .padding(20.dp, 0.dp, 20.dp, 0.dp)
        .background(Color.Gray)
    )
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(48.dp)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.home_icon
                ),
                contentDescription = "Home",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(48.dp)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.pie_graph_icon
                ),
                contentDescription = "Statistics",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        IconButton(
            onClick = {
                navController.navigate("AddTransactionScreen")
            },
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .weight(1f)
        ) {
            Surface (
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.add_icon
                    ),
                    contentDescription = "add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(48.dp)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.piggy_icon
                ),
                contentDescription = "Funds",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(48.dp)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.wallet_icon
                ),
                contentDescription = "Budget",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}