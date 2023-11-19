package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.BudgetViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
    budgetViewModel: BudgetViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val budget :List<SimpleDocument>

    val budgetsState by budgetViewModel.budgetsFetchState.collectAsState()

    val uid = viewModel.loginUiState.value.user?.uid

    LaunchedEffect(
        key1 = Unit,
        block = {

            if (uid != null) {
                budgetViewModel.fetchBudgets(uid)
            }
        }
    )
    when(val state = budgetsState) {
        is DataUiState.Loading -> {
            LoadingScreen()
        }
        is DataUiState.Success -> {
            budget = state.data
            if (state.data.isNotEmpty()) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Text(text = "Budgets")
                }
            }
            else {
                Column (
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = "No tienes budgets configurados, agregalos en ajustes",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        is DataUiState.Error -> {
            Text(text = "Error")
        }
    }
}