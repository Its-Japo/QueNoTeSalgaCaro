package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.ErrorScreen
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.BudgetViewModel

@SuppressLint("StateFlowValueCalledInComposition")
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
        }
    }

    Scaffold (
        topBar = {
            TopBar(title = stringResource(id = R.string.ConfigureBudget), navController = navController)
        }
    ) {
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            when (val state = budgetConfig) {
                is DataUiState.Success -> {
                    LazyColumn (
                        contentPadding = it
                    ) {
                        item {
                            Text(
                                text = stringResource(id = R.string.Income),
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(10.dp, 5.dp, 10.dp, 5.dp),
                                fontSize = 20.sp,
                                color = Color.LightGray
                            )
                            Row (
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = stringResource(id = R.string.Concept),
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = stringResource(id = R.string.Amount),
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                IconButton(
                                    onClick = {
                                        navController.navigate("AddBudgetRowScreen/income/$id")
                                    },
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                ) {
                                    Icon (
                                        painter = painterResource(id = R.drawable.add_icon),
                                        contentDescription = null,
                                        tint = Color.Green
                                    )

                                }
                            }
                            Spacer(modifier = modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                .background(Color.Gray)
                            )
                        }


                        items(state.data.income.size) { index ->
                            Row (
                                modifier = modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = state.data.income[index].concept,
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = state.data.income[index].amount,
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                IconButton(
                                    onClick = {
                                        budgetViewModel.deleteRow(userUid, id, "income", state.data.income[index].id)
                                    },
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                                    Icon (
                                        painter = painterResource(id = R.drawable.outline_delete_24),
                                        contentDescription = null,
                                        tint = Color.Red,
                                        modifier = modifier
                                            .scale(1.3f)
                                    )
                                }
                            }
                        }

                        item {
                            Text(
                                text = stringResource(id = R.string.FixedExpenses),
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(10.dp, 5.dp, 10.dp, 5.dp),
                                fontSize = 20.sp,
                                color = Color.LightGray
                            )
                            Row (
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = stringResource(id = R.string.Concept),
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = stringResource(id = R.string.Amount),
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                IconButton(
                                    onClick = {
                                        navController.navigate("AddBudgetRowScreen/fixedExpenses/$id")
                                    },
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                ) {
                                    Icon (
                                        painter = painterResource(id = R.drawable.add_icon),
                                        contentDescription = null,
                                        tint = Color.Green
                                    )

                                }
                            }
                            Spacer(modifier = modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                .background(Color.Gray)
                            )
                        }


                        items(state.data.fixedExpenses.size) { index ->
                            Row (
                                modifier = modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = state.data.fixedExpenses[index].concept,
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = state.data.fixedExpenses[index].amount,
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                IconButton(
                                    onClick = {
                                        budgetViewModel.deleteRow(userUid, id, "fixedExpenses", state.data.fixedExpenses[index].id)
                                    },
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                                    Icon (
                                        painter = painterResource(id = R.drawable.outline_delete_24),
                                        contentDescription = null,
                                        tint = Color.Red,
                                        modifier = modifier
                                            .scale(1.3f)
                                    )
                                }
                            }
                        }

                        item {
                            Text(
                                text = stringResource(id = R.string.VariableExpenses),
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(10.dp, 5.dp, 10.dp, 5.dp),
                                fontSize = 20.sp,
                                color = Color.LightGray
                            )
                            Row (
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = stringResource(id = R.string.Concept),
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = stringResource(id = R.string.Amount),
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                IconButton(
                                    onClick = {
                                        navController.navigate("AddBudgetRowScreen/variableExpenses/$id")
                                    },
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                ) {
                                    Icon (
                                        painter = painterResource(id = R.drawable.add_icon),
                                        contentDescription = null,
                                        tint = Color.Green
                                    )

                                }
                            }
                            Spacer(modifier = modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                .background(Color.Gray)
                            )
                        }


                        items(state.data.variableExpenses.size) { index ->
                            Row (
                                modifier = modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = state.data.variableExpenses[index].concept,
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                Text(text = "${state.data.variableExpenses[index].amount} ± ${((state.data.variableExpenses[index].amount.toFloat())/10)}",
                                    modifier = modifier
                                        .weight(2f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                )
                                IconButton(
                                    onClick = {
                                        budgetViewModel.deleteRow(userUid, id, "variableExpenses", state.data.variableExpenses[index].id)
                                    },
                                    modifier = modifier
                                        .weight(1f)
                                        .padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                                    Icon (
                                        painter = painterResource(id = R.drawable.outline_delete_24),
                                        contentDescription = null,
                                        tint = Color.Red,
                                        modifier = modifier
                                            .scale(1.3f)
                                    )
                                }
                            }

                        }
                    }

                }
                is DataUiState.Error -> {
                    ErrorScreen(error = (budgetConfig as DataUiState.Error).exception, paddingValues = it)
                }
                is DataUiState.Loading -> {
                    LoadingScreen(paddingValues = it)
                }
            }
        }
    }
}
