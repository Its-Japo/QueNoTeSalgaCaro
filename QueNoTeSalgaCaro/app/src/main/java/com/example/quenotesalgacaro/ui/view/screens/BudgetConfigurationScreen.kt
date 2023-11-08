package com.example.quenotesalgacaro.ui.view.screens

import BudgetViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun BudgetConfigurationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    budgetViewModel: BudgetViewModel = viewModel(),
    id: String
) {

    val budgetConfig by budgetViewModel.budgetConfigurationFetchState.collectAsState()

    val userUid = authViewModel.loginUiState.value.user?.uid
    if (userUid != null) {
        LaunchedEffect(Unit) {
            budgetViewModel.fetchBudgetConfiguration(userUid, id)
            println("BudgetConfiguraton $budgetConfig")
        }
    }

    Scaffold (
        topBar = {
            TopBar(title = "Configure Budget", navController = navController)
        }
    ) {
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp, 60.dp, 10.dp, 10.dp),
        ) {
            when (val state = budgetConfig) {
                is DataUiState.Success -> {
                    Text(text = "Ingresos")
                    Row {
                        Text(text = "Concepto",
                            modifier = modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )
                        Text(text = "Monto",
                            modifier = modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )
                    }
                    LazyColumn {
                        items(state.data.income.size) { index ->
                            Row {
                                Text(text = state.data.income[index].concept,
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = state.data.income[index].amount,
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                            }

                        }
                    }

                    Text(text = "Gastos Fijos")
                    Row {
                        Text(text = "Concepto",
                            modifier = modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )
                        Text(text = "Monto",
                            modifier = modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )
                    }
                    LazyColumn {
                        items(state.data.fixedExpenses.size) { index ->
                            Row {
                                Text(text = state.data.fixedExpenses[index].concept,
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = state.data.fixedExpenses[index].amount,
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                            }

                        }
                    }

                    Text(text = "Gastos Variables")
                    Row {
                        Text(text = "Concepto",
                            modifier = modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )
                        Text(text = "Monto",
                            modifier = modifier
                                .weight(1f)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        )
                    }

                    LazyColumn {
                        items(state.data.variableExpenses.size) { index ->
                            Row {
                                Text(text = state.data.variableExpenses[index].concept,
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = "${state.data.variableExpenses[index].amount} ± ${((state.data.variableExpenses[index].amount.toInt())/10).toString()}",
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                            }

                        }
                    }
                }
                is DataUiState.Error -> {
                    Text(text = "Error")
                }
                is DataUiState.Loading -> {
                    Column (
                        modifier = modifier
                            .fillMaxSize()
                            .padding(10.dp, 10.dp, 10.dp, 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        CircularProgressIndicator(
                            modifier = modifier
                                .scale(1.3f)
                        )
                    }
                }
            }
        }
    }
}