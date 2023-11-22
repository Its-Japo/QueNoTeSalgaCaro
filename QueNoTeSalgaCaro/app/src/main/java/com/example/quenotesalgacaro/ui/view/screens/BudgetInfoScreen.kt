package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.ui.view.composables.ErrorScreen
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.BudgetViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    budgetViewModel: BudgetViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    val budgets :List<SimpleDocument>
    var expandedBudget by remember { mutableStateOf(false) }

    val budgetsState by budgetViewModel.budgetsFetchState.collectAsState()
    val budgetDataState by budgetViewModel.budgetConfigurationFetchState.collectAsState()

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
            budgets = state.data
            if (state.data.isNotEmpty()) {
                var selectedBudget by remember { mutableStateOf( budgets.first() ) }

                LaunchedEffect(key1 = selectedBudget) {
                    if (uid != null) {
                        budgetViewModel.fetchBudgetConfiguration(uid, selectedBudget.name)
                    }
                }

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expandedBudget,
                        onExpandedChange = {
                            expandedBudget = !expandedBudget
                        },
                        modifier = modifier
                            .padding(12.dp)
                            .align(alignment = Alignment.CenterHorizontally),

                        ) {

                        TextField(
                            value = selectedBudget.name,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedBudget) },
                            modifier = modifier
                                .menuAnchor()
                                .width(250.dp),
                            label = {
                                Text(
                                    text = stringResource(id = R.string.PlanBudget),
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                disabledContainerColor = MaterialTheme.colorScheme.surface,
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedBudget,
                            onDismissRequest = { expandedBudget= false }
                        ) {
                            budgets.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.name) },
                                    onClick = {
                                        selectedBudget = item
                                        expandedBudget = false
                                    }
                                )
                            }
                        }
                    }

                    when (val state1 = budgetDataState) {
                        is DataUiState.Loading -> {
                            LoadingScreen(paddingValues = paddingValues)
                        }
                        is DataUiState.Error -> {
                            ErrorScreen(error = state1.exception, paddingValues = paddingValues)
                        }
                        is DataUiState.Success -> {

                            val monthlyAvailable = state1.data.income.sumOf { it.amount.toDouble() } - state1.data.fixedExpenses.sumOf { it.amount.toDouble() } - state1.data.variableExpenses.sumOf { it.amount.toDouble() }
                            val progress = (state1.data.fixedExpenses.sumOf { it.amount.toDouble() } + state1.data.variableExpenses.sumOf { it.amount.toDouble() }) / state1.data.income.sumOf { it.amount.toDouble() }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(0.dp),
                            ){
                                Column(
                                    modifier = modifier
                                        .padding(12.dp, 12.dp, 0.dp, 12.dp)
                                        .weight(2f),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.MonthlyAvailable),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(

                                        text = "Q$monthlyAvailable",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Box(
                                    modifier = modifier.weight(2f),
                                ) {
                                    CircularProgressIndicator(
                                        progress = { progress.toFloat() },
                                        modifier = modifier
                                            .width(120.dp)
                                            .aspectRatio(1f)
                                            .align(alignment = Alignment.Center)
                                            .padding(12.dp),
                                        color = when (progress) {
                                            in 0.0..0.5 -> Color(57, 255, 20)
                                            in 0.5..0.8 -> Color(255, 255, 0)
                                            in 0.8..0.99 -> Color(255, 0, 0)
                                            else -> {
                                                Color(80, 0, 0)
                                            }
                                        },
                                        strokeWidth = 16.dp,
                                    )
                                    Text(
                                        text = "${(progress * 100).toInt()}%",
                                        style = MaterialTheme.typography.displaySmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = modifier.align(alignment = Alignment.Center)
                                    )
                                }
                            }
                            LazyColumn {
                                item {
                                    Text(
                                        text = stringResource(id = R.string.Income),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = modifier.padding(12.dp, 12.dp, 0.dp, 12.dp)
                                    )
                                    Spacer(modifier = modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                        .background(Color.Gray)
                                    )
                                    Row{
                                        Text(
                                            text = stringResource(id = R.string.Category),
                                            modifier = modifier
                                                .weight(3f)
                                                .padding(12.dp)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.Amount),
                                            modifier = modifier
                                                .weight(2f)
                                                .padding(12.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                items(state1.data.income.size) { index ->
                                    Row {

                                        Text(
                                            text = state1.data.income[index].concept,
                                            modifier = modifier
                                                .weight(3f)
                                                .padding(5.dp)
                                        )
                                        Text(
                                            text = state1.data.income[index].amount,
                                            modifier = modifier
                                                .weight(2f)
                                                .padding(5.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                item {
                                    Spacer(modifier = modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                        .background(Color.Gray)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.FixedExpenses),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = modifier.padding(12.dp, 12.dp, 0.dp, 12.dp)
                                    )
                                    Spacer(modifier = modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                        .background(Color.Gray)
                                    )
                                    Row{
                                        Text(
                                            text = stringResource(id = R.string.Category),
                                            modifier = modifier
                                                .weight(3f)
                                                .padding(12.dp)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.Amount),
                                            modifier = modifier
                                                .weight(2f)
                                                .padding(12.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                items( state1.data.fixedExpenses.size) { index ->
                                    Row {

                                        Text(
                                            text = state1.data.fixedExpenses[index].concept,
                                            modifier = modifier
                                                .weight(3f)
                                                .padding(5.dp)
                                        )
                                        Text(
                                            text = state1.data.fixedExpenses[index].amount,
                                            modifier = modifier
                                                .weight(2f)
                                                .padding(5.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                item{
                                    Spacer(modifier = modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                        .background(Color.Gray)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.VariableExpenses),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = modifier.padding(12.dp, 12.dp, 0.dp, 12.dp)
                                    )
                                    Spacer(modifier = modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                        .background(Color.Gray)
                                    )
                                    Row{
                                        Text(
                                            text = stringResource(id = R.string.Category),
                                            modifier = modifier
                                                .weight(3f)
                                                .padding(12.dp)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.AmountV),
                                            modifier = modifier
                                                .weight(2f)
                                                .padding(12.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                items(state1.data.variableExpenses.size) { index ->
                                    Row {
                                        Text(
                                            text = state1.data.variableExpenses[index].concept,
                                            modifier = modifier
                                                .weight(3f)
                                                .padding(5.dp)
                                        )
                                        Text(
                                            text = "${state1.data.variableExpenses[index].amount} ± ${state1.data.variableExpenses[index].amount.toFloat()/10}",
                                            modifier = modifier
                                                .weight(2f)
                                                .padding(5.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }

                    }
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
                        text = stringResource(id = R.string.NoBudgetsConfig),
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(16.dp)
                    )
                    Button(
                        onClick = { navController.navigate("BudgetsScreen") },
                        modifier = modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.extraLarge,
                            )
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.extraLarge,
                            )
                            .padding(4.dp, 2.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(text = stringResource(id = R.string.ConfigureNow))
                    }
                }
            }
        }
        is DataUiState.Error -> {
            Text(text = stringResource(id = R.string.Error))
        }
    }
}