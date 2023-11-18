package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quenotesalgacaro.ui.view.composables.DatePickerDialogD
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AddTransactionScreen(
    //navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
    paddingValues: PaddingValues
){
    val context = LocalContext.current
    val wallets = arrayOf("Wallet 1", "Wallet 2", "Wallet 3", "Wallet 4", "Wallet 5")
    val dates = arrayOf("Ago 2023", "Sep 2023", "Oct 2023", "Nov 2023", "Dic 2023")
    val categories = arrayOf("Category 1", "Category 2", "Category 3", "Category 4", "Category 5")
    var selectedWallet by remember { mutableStateOf(wallets[0]) }
    var selectedDate by remember { mutableStateOf(dates[0]) }
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var expandedDate by remember { mutableStateOf(false) }
    var expandedWallet by remember { mutableStateOf(false) }
    var expandedCategory by remember { mutableStateOf(false) }
    var seletedDate by remember { mutableStateOf("") }
    val descriptionText = remember { mutableStateOf(TextFieldValue()) }
    val montoText = remember { mutableStateOf(TextFieldValue()) }



    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
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
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWallet) },
                    modifier = modifier
                        .menuAnchor()
                        .width(170.dp),
                    label = {
                        Text(
                            text = "Wallet:",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
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
            DatePickerDialogD(onDateSelected = { seletedDate = it })
        }

        ExposedDropdownMenuBox(
            expanded = expandedCategory,
            onExpandedChange = { expandedCategory = !expandedCategory },
            modifier = modifier
                .padding(12.dp)
        ) {
            TextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                modifier = modifier
                    .menuAnchor()
                    .width(250.dp),
                label = {
                    Text(
                        text = "Categorías:",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                )
            )
            ExposedDropdownMenu(
                expanded = expandedCategory,
                onDismissRequest = { expandedCategory = false }
            ) {
                dates.forEach { item ->
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(text = item) },
                        onClick = {
                            selectedCategory = item
                            expandedCategory = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }

        TextField(
            value = descriptionText.value,
            onValueChange = {descriptionText.value = it},
            modifier = modifier
                .padding(12.dp)
                .width(300.dp)
                .height(100.dp),
            label = {
                Text(
                    text = "Descripción:",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            )
        )

        TextField(
            value = montoText.value,
            onValueChange = {montoText.value = it},
            modifier = modifier
                .padding(12.dp)
                .width(300.dp),
            label = {
                Text(
                    text = "Monto:",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .padding(12.dp)
                    .width(300.dp),
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Text(
                    text = "Agregar",
                    modifier = modifier.padding(12.dp),
                )
            }
        }

    }

}
