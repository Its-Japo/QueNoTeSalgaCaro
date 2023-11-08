package com.example.quenotesalgacaro.ui.view.screens

import FundViewModel
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.composables.InfoBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.ui.view.composables.ButtonBar
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FundsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
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
            TopBar(title = "Funds", navController = navController)
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
            is DataUiState.Loading -> CircularProgressIndicator()
            is DataUiState.Success -> {
                LazyColumn(contentPadding = paddingValues) {
                    items(state.data) { wallet ->
                        InfoBar(text = wallet.name, onClick = { /*TODO*/ })
                    }
                }
            }
            is DataUiState.Error -> Text("Error: ${state.exception.message}")
            else -> Text("Something went wrong")
        }
    }

}