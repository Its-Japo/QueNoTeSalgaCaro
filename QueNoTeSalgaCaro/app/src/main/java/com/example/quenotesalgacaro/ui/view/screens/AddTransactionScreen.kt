package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.quenotesalgacaro.navigation.TopBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AddTransactionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
){
    Scaffold(
        topBar = {
            TopBar(title = "Add Transaction", navController = navController)
        }
    ){
        val context = LocalContext.current
        val wallets = arrayOf("Wallet 1", "Wallet 2", "Wallet 3", "Wallet 4", "Wallet 5")
        val dates = arrayOf("Ago 2023", "Sep 2023", "Oct 2023", "Nov 2023", "Dic 2023")
        var selectedWallet by remember { mutableStateOf(wallets[0]) }
        var selectedDate by remember { mutableStateOf(dates[0]) }
        var expandedDate by remember { mutableStateOf(false) }
        var expandedWallet by remember { mutableStateOf(false) }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp, 60.dp, 10.dp, 84.dp)
        ){
            Row(
                modifier = modifier
                    .fillMaxWidth()
            )
            {
                ExposedDropdownMenuBox(
                    expanded = expandedWallet,
                    onExpandedChange = { expandedWallet = !expandedWallet },
                    modifier = modifier
                        .padding(12.dp)
                ) {
                    TextField(
                        value = selectedWallet,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDate) },
                        modifier = modifier
                            .menuAnchor()
                            .width(180.dp),
                        label = {
                            Text(
                                text = "Wallet:",
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedWallet,
                        onDismissRequest = { expandedWallet = false }
                    ) {
                        dates.forEach { item ->
                            DropdownMenuItem(
                                text = { androidx.compose.material3.Text(text = item) },
                                onClick = {
                                    selectedWallet = item
                                    expandedWallet = false
                                    Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
