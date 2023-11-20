package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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
fun AddBudgetRowScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    budgetViewModel: BudgetViewModel = viewModel(),
    operation: String,
    budgetName: String
) {
    val amount = remember { mutableStateOf("") }
    val concept = remember { mutableStateOf("") }

    val addRowConfig by budgetViewModel.addRowState.collectAsState()


    val userUid = authViewModel.loginUiState.value.user?.uid

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.AddBudgetRow), navController = navController)
        }
    ){
        paddingValues ->
        when(addRowConfig){
            is DataUiState.Loading -> {
                LoadingScreen(paddingValues = paddingValues)
            }
            is DataUiState.Success -> {
                Column (
                    modifier = modifier
                        .padding(paddingValues),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = amount.value,
                        onValueChange = { amount.value = it },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = {
                            androidx.compose.material.Text(
                                text = stringResource(id = R.string.Amount),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                        ),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    TextField(
                        value = concept.value,
                        onValueChange = { concept.value = it },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = {
                            androidx.compose.material.Text(
                                text = stringResource(id = R.string.Concept),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                        ),
                        maxLines = 1
                    )
                    Button(
                        onClick = {
                            if (userUid != null) {
                                budgetViewModel.addRow(userUid, budgetName, operation, amount.value.toFloat(), concept.value)
                                navController.navigateUp()
                            }
                        },
                        modifier = modifier
                            .padding(12.dp)
                            .width(300.dp),
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Blue
                        ),
                    ) {
                        androidx.compose.material.Text(
                            text = stringResource(id = R.string.Add),
                            modifier = modifier.padding(12.dp)
                        )
                    }



                }
            }
            is DataUiState.Error -> {
                ErrorScreen(error = (addRowConfig as DataUiState.Error).exception, paddingValues = paddingValues)
            }
        }


    }
}