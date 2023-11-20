package com.example.quenotesalgacaro.ui.view.screens

import com.example.quenotesalgacaro.ui.view.composables.DatePickerWithoutDays
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.ui.view.composables.ErrorScreen
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.WalletViewModel
import java.util.Calendar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    walletViewModel: WalletViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    var expandedWallet by remember { mutableStateOf(false) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    val months = listOf(
        "jan", "feb", "mar", "apr", "may", "jun",
        "jul", "aug", "sep", "oct", "nov", "dec"
    )
    val uid = viewModel.loginUiState.value.user?.uid


    LaunchedEffect(
        key1 = Unit,
        block = {

            if (uid != null) {
                walletViewModel.fetchWallets(uid)
            }
        }
    )

    val walletsState by walletViewModel.walletsFetchState.collectAsState()
    val transactionState by walletViewModel.fetchTransactionsState.collectAsState()

    when(val state = walletsState) {
        is DataUiState.Loading -> {
            LoadingScreen()
        }
        is DataUiState.Success -> {
            if (state.data.isNotEmpty()) {
                var selectedDate by remember { mutableStateOf("${months[currentMonth]}.-$currentYear") }
                var selectedWallet by remember { mutableStateOf(state.data.first().name) }

                LaunchedEffect(selectedWallet, selectedDate) {
                    if (uid != null) {
                        walletViewModel.fetchTransactions(uid, selectedWallet, selectedDate)
                    }
                }

                Column (
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(paddingValues = paddingValues),
                ) {
                    Row (
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(0.dp, 4.dp, 0.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text = stringResource(id = R.string.SelectDate), modifier = modifier.padding(12.dp))
                    }
                    Row (
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        DatePickerWithoutDays(onDateSelected = { year, month ->
                            selectedDate = "${month}.-${year}"
                        })
                    }

                    Row {
                        Column(
                            modifier = modifier
                                .weight(5f)
                                .padding(12.dp),
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = expandedWallet,
                                onExpandedChange = {
                                    expandedWallet = !expandedWallet
                                },
                                modifier = modifier
                                    .padding(12.dp)
                                    .align(alignment = Alignment.CenterHorizontally),

                                ) {

                                TextField(
                                    value = selectedWallet,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWallet) },
                                    modifier = modifier
                                        .menuAnchor(),
                                    label = {
                                        Text(
                                            text = stringResource(id = R.string.Wallet)
                                        )
                                    },
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedWallet,
                                    onDismissRequest = { expandedWallet = false }
                                ) {
                                    state.data.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item.name) },
                                            onClick = {
                                                selectedWallet = item.name
                                                expandedWallet = false
                                                if (uid != null) {
                                                    walletViewModel.fetchTransactions(uid, selectedWallet, selectedDate)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                            Column(
                                modifier = modifier.padding(12.dp, 12.dp, 0.dp, 12.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.Balance),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                when(val tState = transactionState) {
                                    is DataUiState.Loading -> {
                                        Text(text = stringResource(id = R.string.Loading))
                                    }
                                    is DataUiState.Error -> {
                                        Text(text = stringResource(id = R.string.Error))
                                    }
                                    is DataUiState.Success -> {
                                        val balance = tState.data.filter { it.amount >= 0 }.sumOf { it.amount } + tState.data.filter { it.amount < 0 }.sumOf { it.amount }
                                        Text(
                                            text = "Q$balance",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                            }
                        }
                        Box(
                            modifier = modifier.weight(4f),
                        ) {
                            when(val tState = transactionState) {
                                is DataUiState.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = modifier
                                            .width(160.dp)
                                            .aspectRatio(1f)
                                            .align(alignment = Alignment.Center)
                                            .padding(12.dp),
                                        strokeWidth = 16.dp,
                                    )
                                }
                                is DataUiState.Error -> {
                                    ErrorScreen(
                                        error = tState.exception,
                                        paddingValues = paddingValues
                                    )
                                }
                                is DataUiState.Success -> {
                                    val progress = -tState.data.filter { it.amount < 0 }.sumOf { it.amount }/tState.data.filter { it.amount >= 0 }.sumOf { it.amount }


                                    CircularProgressIndicator(
                                        progress = { progress.toFloat() },
                                        modifier = modifier
                                            .width(160.dp)
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

                        }
                    }
                    Row {
                        Text(
                            text = stringResource(id = R.string.Day),
                            modifier = modifier
                                .weight(1f)
                                .padding(12.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
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
                                .padding(12.dp)
                        )
                        /*
                        Text(
                            text = "",
                            modifier = modifier
                                .weight(1f)
                                .padding(12.dp)
                        )*/
                    }
                    Spacer(modifier = modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 0.dp)
                        .background(Color.Gray)
                    )
                    when(val tState = transactionState) {
                        is DataUiState.Loading -> {
                            LoadingScreen(paddingValues = paddingValues)
                        }
                        is DataUiState.Error -> {
                            ErrorScreen(error = tState.exception, paddingValues = paddingValues)
                        }
                        is DataUiState.Success -> {
                            LazyColumn(
                                modifier = modifier
                                    .fillMaxWidth()
                            ) {
                                if (tState.data.isNotEmpty()) {

                                    items(tState.data.size) { index ->
                                        if (index < tState.data.size) {
                                            Row {

                                                Text(
                                                    text = tState.data.sortedBy { it.day }[index].day.toString(),
                                                    modifier = modifier
                                                        .weight(1f)
                                                        .padding(12.dp),
                                                    style = MaterialTheme.typography.labelLarge,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Text(
                                                    text = tState.data.sortedBy { it.day }[index].category,
                                                    modifier = modifier
                                                        .weight(3f)
                                                        .padding(12.dp)
                                                )
                                                Text(
                                                    text = tState.data.sortedBy { it.day }[index].amount.toString(),
                                                    modifier = modifier
                                                        .weight(2f)
                                                        .padding(12.dp)
                                                )
                                                /*IconButton(
                                                    onClick = {  },
                                                    modifier = modifier
                                                        .weight(1f)
                                                ) {
                                                    Icon(
                                                        painter = painterResource(
                                                            id = R.drawable.option
                                                        ),
                                                        contentDescription = "options",
                                                        tint = MaterialTheme.colorScheme.primary
                                                    )
                                                }*/
                                            }
                                        }
                                    }
                                } else {
                                    item {
                                        Column (
                                            modifier = modifier
                                                .fillMaxSize()
                                                .padding(paddingValues),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally

                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.YouHaveNoTransactionsOnThisDate),
                                                textAlign = TextAlign.Center,
                                                modifier = modifier
                                                    .background(
                                                        Brush.verticalGradient(
                                                            colors = listOf(
                                                                MaterialTheme.colorScheme.primary,
                                                                MaterialTheme.colorScheme.primary
                                                            )
                                                        ),
                                                        MaterialTheme.shapes.large,
                                                        0.7F
                                                    )
                                                    .border(
                                                        width = 1.dp,
                                                        color = MaterialTheme.colorScheme.primary,
                                                        shape = MaterialTheme.shapes.large
                                                    )
                                                    .padding(12.dp)

                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(
                        modifier = modifier
                            .height(61.dp)
                            .fillMaxWidth()
                    )
                }
            } else {
                Column (
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        text = stringResource(id = R.string.YouHaveNoWalletsConfigured),
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(16.dp)
                    )
                    Button(
                        onClick = { navController.navigate("WalletsScreen") },
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
                        Text(text = stringResource(id = R.string.Configure))
                    }
                }
            }
            
            
        }
        is DataUiState.Error -> {
            ErrorScreen(error = state.exception, paddingValues = paddingValues)
        }
    }



}