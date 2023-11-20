package com.example.quenotesalgacaro.ui.view.screens

import com.example.quenotesalgacaro.ui.view.vms.FundViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.data.networking.FundData
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.composables.PersistentPlaceholderTextField
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.R

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FundConfigurationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    fundViewModel: FundViewModel = viewModel(),
    fund: String
) {
    val fundState by fundViewModel.fetchOneFundState.collectAsState()

    val initialCapital = remember { mutableStateOf(TextFieldValue()) }
    val goal = remember { mutableStateOf(TextFieldValue()) }
    val annualCapitalization = remember { mutableStateOf(TextFieldValue()) }
    val interest = remember { mutableStateOf(TextFieldValue()) }

    val user = authViewModel.loginUiState.value.user

    LaunchedEffect(user) {
        user?.let {
            fundViewModel.fetchOneFund(it.uid, fund)
        }

    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(title = stringResource(id = R.string.ConfigureFund), navController = navController)
        },
    )
    {
        paddingValues ->
        when(val state = fundState) {
            is DataUiState.Loading -> {
                LoadingScreen(paddingValues = paddingValues)
            }
            is DataUiState.Success -> {

                Column (
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row (
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PersistentPlaceholderTextField(
                            value = initialCapital.value,
                            onValueChange = {
                                initialCapital.value = it
                            },
                            placeholder = state.data.initialCapital.toString(),
                            width = 170,
                            label = stringResource(id = R.string.InitialCapital),
                            keyboardType = KeyboardType.Number
                        )
                        PersistentPlaceholderTextField(
                            value = goal.value,
                            onValueChange = {
                                goal.value = it
                            },
                            placeholder = state.data.goal.toString(),
                            width = 170,
                            label = stringResource(id = R.string.Goal),
                            keyboardType = KeyboardType.Number
                        )
                    }

                    PersistentPlaceholderTextField(
                        value = annualCapitalization.value,
                        onValueChange = {
                            annualCapitalization.value = it
                        },
                        placeholder = state.data.anualcapitalizations.toString(),
                        width = 200,
                        label = stringResource(id = R.string.AnnualCapitalization),
                        keyboardType = KeyboardType.Number
                    )
                    PersistentPlaceholderTextField(
                        value = interest.value,
                        onValueChange = {
                            interest.value = it
                        },
                        placeholder = state.data.interest.toString(),
                        width = 200,
                        label = stringResource(id = R.string.InterestRate),
                        keyboardType = KeyboardType.Number
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
                                if (user != null) {
                                    val newData = FundData(
                                        id = state.data.id,
                                        name = state.data.name,
                                        initialCapital = if(initialCapital.value.text.isNotEmpty()) initialCapital.value.text.toDouble() else state.data.initialCapital,
                                        interest = if(interest.value.text.isNotEmpty()) interest.value.text.toDouble() else state.data.interest,
                                        anualcapitalizations = if(annualCapitalization.value.text.isNotEmpty()) annualCapitalization.value.text.toInt() else state.data.anualcapitalizations,
                                        goal = if(goal.value.text.isNotEmpty()) goal.value.text.toDouble() else state.data.goal
                                    )
                                    fundViewModel.updateFund(user.uid, fund, newData)
                                    navController.navigateUp()
                                }
                            },
                            modifier = modifier
                                .padding(12.dp)
                                .width(300.dp),
                            enabled = initialCapital.value.text.isNotEmpty() ||
                            goal.value.text.isNotEmpty() ||
                            annualCapitalization.value.text.isNotEmpty() ||
                            interest.value.text.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Blue
                            ),
                        ) {
                            Text(
                                text = stringResource(id = R.string.Update),
                                modifier = modifier.padding(12.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                }

            }
            is DataUiState.Error -> {

            }
        }

    }
}