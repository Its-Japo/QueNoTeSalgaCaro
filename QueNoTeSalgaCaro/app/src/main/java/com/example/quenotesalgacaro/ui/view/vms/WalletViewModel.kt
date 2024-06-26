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

class WalletViewModel(private val firestoreRepository: DataBaseRepository = FirebaseFirestoreRepository()) : ViewModel() {

    private val _walletsFetchState = MutableStateFlow<DataUiState<List<SimpleDocument>>>(DataUiState.Loading)
    val walletsFetchState = _walletsFetchState.asStateFlow()

    private val _addWalletState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Loading)
    val addWalletState = _addWalletState.asStateFlow()

    private val _addWalletCategoryState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Success(Unit))
    val addWalletCategoryState = _addWalletCategoryState.asStateFlow()

    private val _fetchWalletCategoriesState = MutableStateFlow<DataUiState<List<SimpleDocument>>>(DataUiState.Loading)
    val fetchWalletCategoriesState = _fetchWalletCategoriesState.asStateFlow()

    private val _fetchTransactionsState = MutableStateFlow<DataUiState<List<Transaction>>>(DataUiState.Loading)
    val fetchTransactionsState = _fetchTransactionsState.asStateFlow()

    private val _fetchTransactionsYearState = MutableStateFlow<DataUiState<List<Transaction>>>(DataUiState.Loading)
    val fetchTransactionsYearState = _fetchTransactionsYearState.asStateFlow()


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

    fun deleteWallet(userId: String, walletName: String) {
        viewModelScope.launch {
            try {
                firestoreRepository.deleteFirstGradeSubcollection(userId, "wallets", walletName)
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
            _walletsFetchState.value = DataUiState.Loading
            try {
                val time = date.split("-")
                println(time)
                val day = time[0].toInt()
                val dateTime = time[1].slice(IntRange(0,2)) + "-" + time[2]
                val transaction = Transaction(id = "NOID", amount = amount.toDouble(), category = category, date = dateTime, day = day, description = concept, year = time[2].toInt(), month = time[1].slice(IntRange(0,2)))
                firestoreRepository.addSecondGradeSubcollectionDocumentTransaction(userId, "wallets", walletName, "transactions", transaction)
                fetchWallets(userId)
            } catch (e: Exception) {
                _walletsFetchState.value = DataUiState.Error(e)
            }
        }
    }

    fun fetchTransactions(userId: String, walletName: String, date: String) {
        viewModelScope.launch {
            try {
                _fetchTransactionsState.value = DataUiState.Loading
                val transactions = firestoreRepository.fetchTransactions(userId, walletName, date)
                _fetchTransactionsState.value = DataUiState.Success(transactions.getOrThrow())
            } catch (e: Exception) {
                _fetchTransactionsState.value = DataUiState.Error(e)
            }
        }
    }

    fun fetchTransactionsYear(userId: String, walletName: String, date: String) {
        viewModelScope.launch {
            try {
                val year = date.split("-")[1].toInt()
                println(year)
                _fetchTransactionsYearState.value = DataUiState.Loading
                val transactions = firestoreRepository.fetchTransactionsYear(userId, walletName, year)
                _fetchTransactionsYearState.value = DataUiState.Success(transactions.getOrThrow())
            } catch (e: Exception) {
                _fetchTransactionsYearState.value = DataUiState.Error(e)
            }
        }
    }

    fun deleteTransaction(userId: String, walletName: String, documentId: String) {
        viewModelScope.launch {
            try {
                firestoreRepository.deleteTransaction(userId, "wallets", walletName, documentId)
                fetchWallets(userId)
            } catch (e: Exception) {
                _fetchTransactionsState.value = DataUiState.Error(e)
            }
        }
    }
}
