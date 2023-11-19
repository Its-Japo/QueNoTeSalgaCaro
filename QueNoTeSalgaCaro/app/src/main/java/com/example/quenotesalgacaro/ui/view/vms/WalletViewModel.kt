package com.example.quenotesalgacaro.ui.view.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.data.networking.Transaction
import com.example.quenotesalgacaro.data.repository.DataBaseRepository
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class WalletViewModel(private val firestoreRepository: DataBaseRepository = FirebaseFirestoreRepository()) : ViewModel() {

    private val _walletsFetchState = MutableStateFlow<DataUiState<List<SimpleDocument>>>(DataUiState.Loading)
    val walletsFetchState = _walletsFetchState.asStateFlow()

    private val _addWalletState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Loading)
    val addWalletState = _addWalletState.asStateFlow()

    private val _addWalletCategoryState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Success(Unit))
    val addWalletCategoryState = _addWalletCategoryState.asStateFlow()

    private val _fetchWalletCategoriesState = MutableStateFlow<DataUiState<List<SimpleDocument>>>(DataUiState.Loading)
    val fetchWalletCategoriesState = _fetchWalletCategoriesState.asStateFlow()

    fun fetchWallets(userId: String) {
        viewModelScope.launch {
            _walletsFetchState.value = DataUiState.Loading
            try {
                val result = firestoreRepository.getFirstGradeSubcollection(userId, "wallets")
                if (result.isSuccess) {
                    _walletsFetchState.value = DataUiState.Success(result.getOrThrow())
                } else {
                    _walletsFetchState.value = DataUiState.Error(Exception("Failed to fetch wallets"))
                }
            } catch (e: Exception) {
                _walletsFetchState.value = DataUiState.Error(e)
            }
        }
    }


    fun addWallet(userId: String, walletName: String) {
        viewModelScope.launch {
            try {
                _addWalletState.value = DataUiState.Loading
                firestoreRepository.addFirstGradeSubcollection(userId, walletName, "wallets")
                _addWalletState.value = DataUiState.Success(Unit)
                fetchWallets(userId)
            } catch (e: Exception) {
                _addWalletState.value = DataUiState.Error(e)
            }
        }
    }

    fun addWalletCategory(userId: String, walletName: String, element: String) {
        viewModelScope.launch {
            try {
                _addWalletCategoryState.value = DataUiState.Loading
                firestoreRepository.addSecondGradeSubcollectionDocumentWallet(userId, "wallets", walletName, "categories", SimpleDocument(element))
                _addWalletCategoryState.value = DataUiState.Success(Unit)
                fetchWallets(userId)
            } catch (e: Exception) {
                _addWalletCategoryState.value = DataUiState.Error(e)
            }
        }
    }

    fun fetchWalletCategories(userId: String, walletName: String){
        viewModelScope.launch {
            try {
                _fetchWalletCategoriesState.value = DataUiState.Loading
                val categories = firestoreRepository.getSecondGradeSubcollectionWallet(userId, "wallets", walletName, "categories")
                _fetchWalletCategoriesState.value = DataUiState.Success(categories.getOrThrow())

            } catch (e: Exception) {
                _fetchWalletCategoriesState.value = DataUiState.Error(e)
            }
        }
    }

    fun deleteWalletCategory(userId: String?, walletName: String, rowId: String){
        viewModelScope.launch {
            try {
                firestoreRepository.deleteSecondGradeSubcollectionDocument(userId, "wallets", walletName, "categories", rowId)
                if (userId != null) {
                    fetchWalletCategories(userId, walletName)
                }
            } catch (e: Exception) {
                _fetchWalletCategoriesState.value = DataUiState.Error(e)
            }
        }
    }

    fun addTransaction(userId: String, walletName: String, category: String, amount: String, concept: String, date: String) {
        viewModelScope.launch {
            try {
                val time = date.split("-")
                println(time)
                val day = time[0].toInt()
                val dateTime = time[1] + "-" + time[2]
                val transaction = Transaction(amount.toDouble(), category, dateTime, day, concept)
                firestoreRepository.addSecondGradeSubcollectionDocumentTransaction(userId, "wallets", walletName, "transactions", transaction)

            } catch (e: Exception) {
                _addWalletCategoryState.value = DataUiState.Error(e)
            }
        }
    }
}
