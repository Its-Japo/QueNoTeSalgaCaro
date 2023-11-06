package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.BudgetViewModel
import com.example.quenotesalgacaro.ui.view.vms.FundViewModel
import com.example.quenotesalgacaro.ui.view.vms.WalletViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold (
        topBar = {
            when (actionViewModel) {
                is WalletViewModel -> TopBar(title = "Create Wallet", navController = navController)
                is BudgetViewModel -> TopBar(title = "Create Budget", navController = navController)
                is FundViewModel -> TopBar(title = "Create Fund", navController = navController)
            }
        }
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp, 60.dp, 10.dp, 10.dp)
        ){
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { when (actionViewModel) {
                    is WalletViewModel -> Text(text = "Wallet Name")
                    is BudgetViewModel -> Text(text = "Budget Name")
                    is FundViewModel -> Text(text = "Fund Name")
                }},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                when (actionViewModel) {
                    is WalletViewModel -> Text(text = "Create Wallet")
                    is BudgetViewModel -> Text(text = "Create Budget")
                    is FundViewModel -> Text(text = "Create Fund")
                }
            }


        }
    }
}