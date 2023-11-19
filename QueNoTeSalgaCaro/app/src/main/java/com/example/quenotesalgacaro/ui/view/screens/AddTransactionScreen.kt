package com.example.quenotesalgacaro.ui.view.screens

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.ui.view.composables.DatePickerDialogD
import com.example.quenotesalgacaro.ui.view.composables.LoadingDropdownTextField
import com.example.quenotesalgacaro.ui.view.composables.LoadingScreen
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel
import com.example.quenotesalgacaro.ui.view.vms.WalletViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AddTransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
    walletViewModel: WalletViewModel = viewModel(),
    paddingValues: PaddingValues
){
    val context = LocalContext.current


    val getWalletsState by walletViewModel.walletsFetchState.collectAsState()
    val walletCategoriesState by walletViewModel.fetchWalletCategoriesState.collectAsState()

    val user = viewModel.loginUiState.value.user

    LaunchedEffect(user) {
        user?.let {
            walletViewModel.fetchWallets(it.uid)
        }
    }


    when (getWalletsState) {
        is DataUiState.Loading -> {
            LoadingScreen()
        }
        is DataUiState.Success -> {
            if ((getWalletsState as DataUiState.Success<List<SimpleDocument>>).data.isNotEmpty()) {
                var selectedWallet by remember { mutableStateOf((getWalletsState as DataUiState.Success<List<SimpleDocument>>).data[0].name) }
                var selectedCategory by remember { mutableStateOf("") }

                var expandedWallet by remember { mutableStateOf(false) }
                var expandedCategory by remember { mutableStateOf(false) }
                var seletedDate by remember { mutableStateOf("") }
                val descriptionText = remember { mutableStateOf(TextFieldValue()) }
                val montoText = remember { mutableStateOf(TextFieldValue()) }

                LaunchedEffect(selectedWallet, user) {
                    user?.let {
                        walletViewModel.fetchWalletCategories(it.uid, selectedWallet)
                    }
                }

                LaunchedEffect(walletCategoriesState) {
                    when (walletCategoriesState) {
                        is DataUiState.Loading -> {
                            selectedCategory = "Loading..."
                        }
                        is DataUiState.Success<List<SimpleDocument>> -> {
                            val categories = (walletCategoriesState as DataUiState.Success<List<SimpleDocument>>).data
                            if (categories.isNotEmpty()) {
                                selectedCategory = categories.first().name
                            }
                        }

                        is DataUiState.Error -> {
                            selectedCategory = "Error"
                        }
                    }
                }
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
                        LoadingDropdownTextField(
                            selectedWallet = selectedWallet,
                            dates = when(getWalletsState) {
                                is DataUiState.Success -> {
                                    (getWalletsState as DataUiState.Success<List<SimpleDocument>>).data.map { it.name }.toTypedArray()
                                }
                                else -> {
                                    arrayOf("UNCATEGORIZED")
                                }
                            },
                            expandedWallet = expandedWallet,
                            onExpandedChange = {expandedWallet = !expandedWallet},
                            onItemSelected =
                            {
                                selectedWallet = it
                                expandedWallet = false
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            },
                            uiState = getWalletsState,
                            width = 170,
                            label = "Wallet:"
                        )
                        DatePickerDialogD(onDateSelected = { seletedDate = it })
                    }

                    LoadingDropdownTextField(
                        selectedWallet = selectedCategory,
                        dates = when(walletCategoriesState) {
                            is DataUiState.Success -> {
                                (walletCategoriesState as DataUiState.Success<List<SimpleDocument>>).data.map { it.name }.toTypedArray()
                            }
                            else -> {
                                arrayOf("UNCATEGORIZED")
                            }
                        },
                        expandedWallet = expandedCategory,
                        onExpandedChange = {
                            expandedCategory = !expandedCategory
                        },
                        onItemSelected = {
                            selectedCategory = it
                            expandedCategory = false
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        },
                        uiState = walletCategoriesState,
                        width = 250,
                        label = "Category:"
                    )



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
                            onClick = {
                                if (user != null) {
                                    walletViewModel.addTransaction(
                                        userId = user.uid,
                                        walletName = selectedWallet,
                                        category = selectedCategory,
                                        date = seletedDate,
                                        concept = descriptionText.value.text,
                                        amount = montoText.value.text
                                    )
                                }
                            },
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
            else {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No tienes wallets")
                }
            }
        }
        is DataUiState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(text = "Funds")
            }
        }
    }
}
