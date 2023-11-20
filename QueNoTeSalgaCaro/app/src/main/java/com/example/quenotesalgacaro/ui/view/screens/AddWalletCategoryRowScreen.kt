package com.example.quenotesalgacaro.ui.view.screens

import com.example.quenotesalgacaro.ui.view.vms.WalletViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import com.example.quenotesalgacaro.R
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.ErrorScreen
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel


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
            TopBar(title = stringResource(id = R.string.AddCategory), navController = navController)
        }
    ){
        paddingValues ->
        when(addCategoryState){
            is DataUiState.Loading -> {
                LoadingScreen(paddingValues = paddingValues)
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
                                text = stringResource(id = R.string.Category),
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
                            text = stringResource(id = R.string.Add),
                            modifier = modifier.padding(12.dp)
                        )
                    }



                }
            }
            is DataUiState.Error -> {
                ErrorScreen(error = (addCategoryState as DataUiState.Error).exception, paddingValues = paddingValues)
            }
        }


    }


}