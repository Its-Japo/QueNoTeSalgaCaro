package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.data.networking.FundData
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.ErrorScreen
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.FundViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FundsInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    fundViewModel: FundViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    var expandedFund by remember { mutableStateOf(false) }

    val fundsState by fundViewModel.fundFetchState.collectAsState()
    val fundTransactionState by fundViewModel.fetchFundTransactionsState.collectAsState()

    val uid = viewModel.loginUiState.value.user?.uid


    LaunchedEffect(
        key1 = Unit,
        block = {
            if (uid != null) {
                fundViewModel.fetchFunds(uid)
            }
        }
    )
    val state = fundsState

    Scaffold(
        floatingActionButton = {
            when (state) {
                is DataUiState.Success -> {
                    if (state.data.isNotEmpty()) {
                        FloatingActionButton(
                            modifier = modifier.offset(x = 0.dp, y = (-100.dp)),
                            onClick = {
                                navController.navigate("AddFundTransactionScreen/hola")
                            },
                        ) {
                            Text(text = "+", fontSize = 30.sp)
                        }
                    }
                }

                else -> {

                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ){
        when(state) {
            is DataUiState.Loading -> {
                LoadingScreen(paddingValues = paddingValues)
            }
            is DataUiState.Success -> {
                if (state.data.isNotEmpty()) {
                    val funds = state.data
                    var selectedFund by remember { mutableStateOf(funds[0]) }

                    LaunchedEffect(key1 = selectedFund) {
                        if (uid != null) {
                            fundViewModel.fetchFundTransactions(uid, selectedFund.name)
                        }
                    }

                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(paddingValues = paddingValues)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expandedFund,
                            onExpandedChange = {
                                expandedFund = !expandedFund
                            },
                            modifier = modifier
                                .padding(12.dp)
                                .align(alignment = Alignment.CenterHorizontally),

                            ) {

                            TextField(
                                value = selectedFund.name,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFund) },
                                modifier = modifier
                                    .menuAnchor()
                                    .width(180.dp),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.Plan)
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedFund,
                                onDismissRequest = { expandedFund= false }
                            ) {
                                funds.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item.name) },
                                        onClick = {
                                            selectedFund = item
                                            expandedFund = false
                                        }
                                    )
                                }
                            }
                        }

                        when(val fState = fundTransactionState) {
                            is DataUiState.Loading -> {
                                LoadingScreen(paddingValues = paddingValues)
                            }
                            is DataUiState.Error -> {
                                ErrorScreen(error = fState.exception, paddingValues = paddingValues)
                            }
                            is DataUiState.Success -> {


                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(0.dp),
                                ){
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = modifier
                                            .padding(12.dp, 12.dp, 0.dp, 12.dp)
                                            .weight(2f),
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.Goal),
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "Q${selectedFund.goal}",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer (
                                            modifier = modifier
                                                .height(5.dp)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.Saving),
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "Q${fState.data.sumOf { it.amount }}",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    Box(
                                        modifier = modifier.weight(2f),
                                    ) {

                                        CircularProgressIndicator(
                                            progress = { (fState.data.sumOf { it.amount }/selectedFund.goal).toFloat() },
                                            modifier = modifier
                                                .width(120.dp)
                                                .aspectRatio(1f)
                                                .align(alignment = Alignment.Center)
                                                .padding(12.dp),
                                            color = when ((fState.data.sumOf { it.amount }/selectedFund.goal)) {
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
                                            text = "${(fState.data.sumOf { it.amount }/selectedFund.goal)*100}%",
                                            style = MaterialTheme.typography.displaySmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = modifier.align(alignment = Alignment.Center)
                                        )
                                    }
                                }
                                Spacer(modifier = modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .padding(20.dp, 0.dp, 20.dp, 0.dp)
                                    .background(Color.Gray)
                                )
                                Row(){
                                    Text(
                                        text = stringResource(id = R.string.Date),
                                        modifier = modifier
                                            .weight(2f)
                                            .padding(12.dp),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = stringResource(id = R.string.Type),
                                        modifier = modifier
                                            .weight(3f)
                                            .padding(12.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.Aporte),
                                        modifier = modifier
                                            .weight(2f)
                                            .padding(12.dp)
                                    )
                                    Text(
                                        text = "",
                                        modifier = modifier
                                            .weight(1f)
                                            .padding(12.dp)
                                    )
                                }
                                LazyColumn(
                                    modifier = modifier
                                        .fillMaxWidth()
                                ) {
                                    items(fState.data.size) { index ->
                                        Row {

                                            Text(
                                                text = fState.data[index].date,
                                                modifier = modifier
                                                    .weight(3f)
                                                    .padding(5.dp),
                                                style = MaterialTheme.typography.labelLarge,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = fState.data[index].type,
                                                modifier = modifier
                                                    .weight(3f)
                                                    .padding(5.dp)
                                            )
                                            Text(
                                                text = fState.data[index].amount.toString(),
                                                modifier = modifier
                                                    .weight(2f)
                                                    .padding(5.dp)
                                            )
                                            IconButton(
                                                    onClick = {
                                                        /*TODO*/
                                                    },
                                            modifier = modifier
                                                .weight(1f)
                                            ) {
                                            Icon(
                                                painter = painterResource(
                                                    id = R.drawable.outline_delete_24
                                                ),
                                                contentDescription = stringResource(id = R.string.Delete),
                                                tint = Color.Red
                                            )
                                        }
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
                            text = stringResource(id = R.string.NoFunds),
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(16.dp)
                        )
                        Button(
                            onClick = { navController.navigate("FundsScreen") },
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
                ErrorScreen(error = state.exception, paddingValues = paddingValues)
            }
        }
    }


}