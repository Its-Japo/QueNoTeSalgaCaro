package com.example.quenotesalgacaro.ui.view.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.InfoBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.ui.view.composables.ButtonBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FundsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val items: List<String> = listOf(
        "name", "description", "amount"
    )

    Scaffold (
        topBar = {
            TopBar(title = "Funds", navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                     navController.navigate("CreateScreen/fund")                },
            ) {
                Text(text = "+", fontSize = 30.sp)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyColumn(
            contentPadding = it,
        ) {
            items(items.size) { index ->
                InfoBar(
                    text = items[index],
                    onClick = { /*TODO*/ })
            }
        }

    }

}