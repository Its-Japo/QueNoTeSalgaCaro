package com.example.quenotesalgacaro.ui.view.screens

import com.example.quenotesalgacaro.ui.view.vms.FundViewModel
import com.example.quenotesalgacaro.ui.view.vms.WalletViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.BudgetViewModel
import com.example.quenotesalgacaro.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    actionViewModel: ViewModel = viewModel(),
){

    val name = remember {
        mutableStateOf(TextFieldValue())
    }
    val addWalletState = when (actionViewModel) {
        is WalletViewModel -> {
            actionViewModel.addWalletState.collectAsState().value
        }

        is BudgetViewModel -> {
            actionViewModel.addBudgetState.collectAsState().value
        }

        is FundViewModel -> {
            actionViewModel.addFundState.collectAsState().value
        }

        else -> {
            null
        }
    }



    Scaffold (
        topBar = {
            when (actionViewModel) {
                is WalletViewModel -> TopBar(title = stringResource(id = R.string.CreateWallet), navController = navController)
                is BudgetViewModel -> TopBar(title = stringResource(id = R.string.CreateBudget), navController = navController)
                is FundViewModel -> TopBar(title = stringResource(id = R.string.CreateFund), navController = navController)
            }
        }
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(it)
        ){
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { when (actionViewModel) {
                    is WalletViewModel -> Text(text = stringResource(id = R.string.WalletName))
                    is BudgetViewModel -> Text(text = stringResource(id = R.string.BudgetName))
                    is FundViewModel -> Text(text = stringResource(id = R.string.FundName))
                }},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            )

            Button(
                onClick = {
                    val loginState = authViewModel.loginUiState.value

                    when (actionViewModel) {
                        is WalletViewModel -> {
                            loginState.user?.let { it1 -> actionViewModel.addWallet(it1.uid, name.value.text) }
                        }

                        is BudgetViewModel -> {
                            loginState.user?.let { it1 -> actionViewModel.addBudget(it1.uid, name.value.text) }
                        }

                        is FundViewModel -> {
                            loginState.user?.let { it1 -> actionViewModel.addFund(it1.uid, name.value.text) }
                        }
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                when (actionViewModel) {
                    is WalletViewModel -> Text(text = stringResource(id = R.string.CreateWallet))
                    is BudgetViewModel -> Text(text = stringResource(id = R.string.CreateBudget))
                    is FundViewModel -> Text(text = stringResource(id = R.string.CreateFund))
                }
            }
        }
    }
    LaunchedEffect(key1 = addWalletState) {
        when (actionViewModel) {
            is WalletViewModel -> {
                if (addWalletState is DataUiState.Success<*>) {
                    actionViewModel.fetchWallets(authViewModel.loginUiState.value.user!!.uid)
                    navController.navigateUp()
                }
            }
            is BudgetViewModel -> {
                if (addWalletState is DataUiState.Success<*>) {
                    actionViewModel.fetchBudgets(authViewModel.loginUiState.value.user!!.uid)
                    navController.navigateUp()
                }
            }
            is FundViewModel -> {
                if (addWalletState is DataUiState.Success<*>) {
                    actionViewModel.fetchFunds(authViewModel.loginUiState.value.user!!.uid)
                    navController.navigateUp()
                }
            }
        }

    }
}