package com.example.quenotesalgacaro.ui.view.screens

import WalletViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddWalletCategoryRowScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    walletViewModel: WalletViewModel = viewModel(),
    walletName: String
) {

    val category = remember { mutableStateOf("") }

    val addCategoryState by walletViewModel.addWalletCategoryState.collectAsState()

    val userUid = authViewModel.loginUiState.value.user?.uid

    Scaffold(
        topBar = {
            TopBar(title = "Add Category", navController = navController)
        }
    ){
        paddingValues ->
        when(val state = addCategoryState){
            is DataUiState.Loading -> {
                Column (
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    CircularProgressIndicator(
                        modifier = modifier
                            .scale(1.3f)
                    )
                }
            }
            is DataUiState.Success -> {
                Column (
                    modifier = modifier
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = category.value,
                        onValueChange = { category.value = it },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = {
                            androidx.compose.material.Text(
                                text = "Category",
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
                                walletViewModel.addWalletCategory(userUid, walletName, category.value)
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