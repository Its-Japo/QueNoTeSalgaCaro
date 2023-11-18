package com.example.quenotesalgacaro.ui.view.screens

import BudgetViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
    val amout = remember { mutableStateOf("") }
    val concept = remember { mutableStateOf("") }

    val addRowConfig by budgetViewModel.addRowState.collectAsState()


    val userUid = authViewModel.loginUiState.value.user?.uid

    Scaffold(
        topBar = {
            TopBar(title = "Add Budget Row", navController = navController)
        }
    ){
        paddingValues ->
        when(val state = addRowConfig){
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
                        value = amout.value,
                        onValueChange = { amout.value = it },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = {
                            androidx.compose.material.Text(
                                text = "Amount",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.surface,
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
                                text = "Concept",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        maxLines = 1
                    )
                    Button(
                        onClick = {
                            if (userUid != null) {
                                budgetViewModel.addRow(userUid, budgetName, operation, amout.value.toFloat(), concept.value)
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
                            text = "Agregar",
                            modifier = modifier.padding(12.dp)
                        )
                    }



                }
            }
            is DataUiState.Error -> {
                Column {
                    Text(text = "Error adding row")
                }
            }
        }


    }
}