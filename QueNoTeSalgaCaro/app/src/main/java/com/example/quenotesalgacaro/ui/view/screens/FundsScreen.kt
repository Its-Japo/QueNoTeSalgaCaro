package com.example.quenotesalgacaro.ui.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.InfoBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FundsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val items: List<String> = listOf(
        "name", "description", "amount"
    )

    Scaffold (
        topBar = {
            TopBar(title = "Funds", navController = navController)
        }
    ) {
        LazyColumn(
            contentPadding = it,
        ) {
            items(items.size) { index ->
                InfoBar(text = items[index], onClick = { /*TODO*/ })
            }
        }
    }

}