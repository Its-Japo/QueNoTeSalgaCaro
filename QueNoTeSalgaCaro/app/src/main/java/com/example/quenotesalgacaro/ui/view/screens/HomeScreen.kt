package com.example.quenotesalgacaro.ui.view.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quenotesalgacaro.R
import com.example.quenotesalgacaro.navigation.BottomBar
import com.example.quenotesalgacaro.navigation.TopBar
import com.example.quenotesalgacaro.ui.view.uistates.FilaTabla
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
) {
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.app_name), navController = navController, auth = viewModel.loginUiState.value.user != null)
        },
        bottomBar = {
            BottomBar()
        }
    ) {
        val context = LocalContext.current
        val dates = arrayOf("Ago 2023", "Sep 2023", "Oct 2023", "Nov 2023", "Dic 2023")
        val wallets = arrayOf("Wallet 1", "Wallet 2", "Wallet 3", "Wallet 4", "Wallet 5")
        var expandedDate by remember { mutableStateOf(false) }
        var expandedWallet by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf(dates[0]) }
        var selectedWallet by remember { mutableStateOf(wallets[0]) }

        val saldo = 150430.41
        val total = 12000.00
        val progress = (total - saldo)/total

        var elementosTabla = mutableListOf<FilaTabla>(
            FilaTabla("1", "Comida", "Q100.00"),
            FilaTabla("2", "Comida", "Q100.00"),
            FilaTabla("2","Cine","Q50.00"),
            FilaTabla("3", "Comida", "Q100.00"),
            FilaTabla("4", "Comida", "Q100.00"),
            FilaTabla("5", "Comida", "Q100.00"),
            FilaTabla("6", "Gasolina", "Q400.00"),
            FilaTabla("7", "Comida", "Q100.00"),
            FilaTabla("8", "Comida", "Q100.00"),
            FilaTabla("9","Super","Q2000.00")
        )
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp, 60.dp, 10.dp, 84.dp),
        ) {


            ExposedDropdownMenuBox(
                expanded = expandedDate,
                onExpandedChange = {
                    expandedDate = !expandedDate
                },
                modifier = modifier
                    .padding(12.dp)
                    .align(alignment = Alignment.CenterHorizontally),

                ) {

                TextField(
                    value = selectedDate,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDate) },
                    modifier = modifier
                        .menuAnchor()
                        .width(180.dp),
                    label = {
                        Text(
                            text = "Fecha"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedDate,
                    onDismissRequest = { expandedDate = false }
                ) {
                    dates.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedDate = item
                                expandedDate = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
            Row {
                Column(
                    modifier = modifier
                        .weight(5f)
                        .padding(12.dp),
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expandedWallet,
                        onExpandedChange = {
                            expandedWallet = !expandedWallet
                        },
                        modifier = modifier
                            .padding(12.dp)
                            .align(alignment = Alignment.CenterHorizontally),

                        ) {

                        TextField(
                            value = selectedWallet,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWallet) },
                            modifier = modifier
                                .menuAnchor(),
                            label = {
                                Text(
                                    text = "Wallet"
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
                            wallets.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        selectedWallet = item
                                        expandedWallet = false
                                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                    Column(
                        modifier = modifier.padding(12.dp, 12.dp, 0.dp, 12.dp),
                    ) {
                        Text(
                            text = "Saldo",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Q$saldo",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Box(
                    modifier = modifier.weight(4f),
                ) {
                    CircularProgressIndicator(
                        progress = progress.toFloat(),
                        modifier = modifier
                            .width(160.dp)
                            .aspectRatio(1f)
                            .align(alignment = Alignment.Center)
                            .padding(12.dp),
                        strokeWidth = 16.dp,
                        color = when (progress) {
                            in 0.0..0.5 -> Color(57, 255, 20)
                            in 0.5..0.8 -> Color(255, 255, 0)
                            in 0.8..0.99 -> Color(255, 0, 0)
                            else -> {
                                Color(80, 0, 0)
                            }
                        }
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.align(alignment = Alignment.Center)
                    )
                }
            }
            Row {
                Text(
                    text = "Dia",
                    modifier = modifier
                        .weight(1f)
                        .padding(12.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Cantegoria",
                    modifier = modifier
                        .weight(3f)
                        .padding(12.dp)
                )
                Text(
                    text = "Monto",
                    modifier = modifier
                        .weight(2f)
                        .padding(12.dp)
                )
                Text(
                    text = "",
                    modifier = modifier
                        .weight(1f)
                        .padding(12.dp)
                )
            }
            Spacer(modifier = modifier.height(1.dp)
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 20.dp, 0.dp)
                .background(Color.Gray)
            )
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                items(elementosTabla.size) { index ->
                    Row {

                        Text(
                            text = elementosTabla[index].dia,
                            modifier = modifier
                                .weight(1f)
                                .padding(12.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = elementosTabla[index].categoria,
                            modifier = modifier
                                .weight(3f)
                                .padding(12.dp)
                        )
                        Text(
                            text = elementosTabla[index].monto,
                            modifier = modifier
                                .weight(2f)
                                .padding(12.dp)
                        )
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = modifier
                                .weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.homeicon
                                ),
                                contentDescription = "Opciones",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            Spacer(
                modifier = modifier
                    .height(61.dp)
                    .fillMaxWidth()
            )

        }
    }
}