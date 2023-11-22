package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.FundViewModel
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.data.networking.FundData
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.DatePickerDialogD
import com.example.quenotesalgacaro.ui.view.composables.ErrorScreen
import com.example.quenotesalgacaro.ui.view.composables.LoadingDropdownTextField
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AddFundTransactionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    fundViewModel: FundViewModel = viewModel(),
    fund: String,
){
    val context = LocalContext.current
    val getFundState by fundViewModel.fundFetchState.collectAsState()
    val user = authViewModel.loginUiState.value.user
    val transactionTypes = listOf("Pactado", "Extraordinario")

    LaunchedEffect(user) {
        user?.let {
            fundViewModel.fetchFunds(it.uid)
        }
    }
    Scaffold (
        topBar = {
            TopBar(title = stringResource(id = R.string.FundsTransaction), navController = navController)
        },
    ) {
        when (val state = getFundState) {
            is DataUiState.Loading -> {
                LoadingScreen(paddingValues = it)
            }
            is DataUiState.Success -> {
                if(state.data.isNotEmpty()){
                    var selectedFund by remember { mutableStateOf(state.data.first().name) }
                    var expandedFund by remember { mutableStateOf(false) }

                    var selectedDate by remember { mutableStateOf("") }
                    var selectedType by remember { mutableStateOf(transactionTypes[0]) }
                    var expandedType by remember { mutableStateOf(false) }
                    val amountText = remember { mutableStateOf(TextFieldValue()) }

                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(paddingValues = it),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier
                                .fillMaxWidth()
                        )
                        {
                            LoadingDropdownTextField(
                                selectedWallet = selectedFund,
                                dates = when(getFundState) {
                                    is DataUiState.Success -> {
                                        state.data.map { it.name }.toTypedArray()
                                    }
                                    else -> {
                                        arrayOf(stringResource(id = R.string.NoFunds))
                                    }
                                },
                                expandedWallet = expandedFund,
                                onExpandedChange = {expandedFund = !expandedFund},
                                onItemSelected =
                                {
                                    selectedFund = it
                                    expandedFund = false
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                },
                                uiState = getFundState,
                                width = 170,
                                label = stringResource(id = R.string.Funds),
                            )
                            DatePickerDialogD(onDateSelected = { selectedDate = it })
                        }

                        ExposedDropdownMenuBox(
                            expanded = expandedType,
                            onExpandedChange = {
                                expandedType = !expandedType
                            },
                            modifier = modifier
                                .padding(12.dp)
                                .align(alignment = Alignment.CenterHorizontally),

                            ) {

                            TextField(
                                value = selectedType,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedType) },
                                modifier = modifier
                                    .menuAnchor(),
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.Type)
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedType,
                                onDismissRequest = { expandedType = false }
                            ) {
                                transactionTypes.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            selectedType = item
                                            expandedType = false
                                        }
                                    )
                                }
                            }
                        }

                        TextField(
                            value = amountText.value,
                            onValueChange = {amountText.value = it},
                            modifier = modifier
                                .padding(12.dp)
                                .width(300.dp),
                            label = {
                                Text(
                                    text = stringResource(id = R.string.Aporte),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                disabledContainerColor = MaterialTheme.colorScheme.surface,
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {

                                },
                                modifier = modifier
                                    .padding(12.dp)
                                    .width(300.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                    containerColor = MaterialTheme.colorScheme.primary,
                                ),

                            ) {
                                Text(
                                    text = stringResource(id = R.string.Add),
                                    modifier = modifier.padding(12.dp),
                                )
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.NoBudgetsConfig))
                    }
                }
            }
            is DataUiState.Error -> {
                ErrorScreen(error = (getFundState as DataUiState.Error).exception, paddingValues = it)
            }
        }
    }
}