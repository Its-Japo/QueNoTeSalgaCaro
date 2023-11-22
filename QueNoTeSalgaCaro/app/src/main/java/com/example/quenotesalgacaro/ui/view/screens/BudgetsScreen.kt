package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.InfoBar
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.BudgetViewModel
import com.example.quenotesalgacaro.R


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BudgetsScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    budgetViewModel: BudgetViewModel = viewModel()
) {
    val budgetsFetchState by budgetViewModel.budgetsFetchState.collectAsState()
    val user = authViewModel.loginUiState.value.user

    LaunchedEffect(user) {
        user?.let {
            budgetViewModel.fetchBudgets(it.uid)
        }
    }

    Scaffold (
        topBar = {
            TopBar(title = stringResource(id = R.string.Budgets), navController = navController)
        },
        floatingActionButton = {
            if (budgetsFetchState is DataUiState.Success) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("CreateScreen/budget")
                    },
                ) {
                    Text(text = "+", fontSize = 30.sp)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        paddingValues ->
        when (val state = budgetsFetchState) {
            is DataUiState.Loading -> {
                LoadingScreen(paddingValues = paddingValues)
            }
            is DataUiState.Success -> {
                LazyColumn(contentPadding = paddingValues) {
                    items(state.data) { wallet ->
                        InfoBar(
                            text = wallet.name,
                            onClick1 = {
                                navController.navigate("BudgetConfigurationScreen/${wallet.name}")
                            },
                            onClick2 = {
                                budgetViewModel.deleteBudget(user!!.uid, wallet.name)
                            }
                        )
                    }
                }
            }
            is DataUiState.Error -> Text("Error: ${state.exception.message}")
        }

    }

}

