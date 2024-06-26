package com.example.quenotesalgacaro.ui.view.screens

import com.example.quenotesalgacaro.ui.view.vms.FundViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.InfoBar
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.ui.view.composables.ErrorScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FundsScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    fundViewModel: FundViewModel = viewModel()
) {
    val fundsFetchState by fundViewModel.fundFetchState.collectAsState()
    val user = authViewModel.loginUiState.value.user

    LaunchedEffect(user) {
        user?.let {
            fundViewModel.fetchFunds(it.uid)
        }
    }
    Scaffold (
        topBar = {
            TopBar(title = stringResource(id = R.string.Funds), navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                     navController.navigate("CreateScreen/fund")                },
            ) {
                Text(text = "+", fontSize = 30.sp)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        paddingValues ->
        when (val state = fundsFetchState) {
            is DataUiState.Loading -> LoadingScreen(paddingValues = paddingValues)
            is DataUiState.Success -> {
                LazyColumn(contentPadding = paddingValues) {
                    items(state.data) { fund ->
                        InfoBar(
                            text = fund.name,
                            onClick1 = { navController.navigate("FundConfigurationScreen/${fund.name}") },
                            onClick2 = { fundViewModel.deleteFund(user!!.uid, fund.name) }
                        )
                    }
                }
            }
            is DataUiState.Error -> {
                ErrorScreen(error = state.exception, paddingValues = paddingValues)
            }
        }
    }

}