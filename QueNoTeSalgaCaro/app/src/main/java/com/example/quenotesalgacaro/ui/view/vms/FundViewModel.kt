package com.example.quenotesalgacaro.ui.view.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.networking.FundData
import com.example.quenotesalgacaro.data.networking.FundTransaction
import com.example.quenotesalgacaro.data.repository.DataBaseRepository
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FundViewModel(private val firestoreRepository: DataBaseRepository = FirebaseFirestoreRepository()) : ViewModel() {

    private val _fundsFetchState = MutableStateFlow<DataUiState<List<FundData>>>(DataUiState.Loading)
    val fundFetchState = _fundsFetchState.asStateFlow()

    private val _fetchOneFundState = MutableStateFlow<DataUiState<FundData>>(DataUiState.Loading)
    val fetchOneFundState = _fetchOneFundState.asStateFlow()

    private val _addFundsState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Loading)
    val addFundState = _addFundsState.asStateFlow()

    private val _fetchFundTransactionsState = MutableStateFlow<DataUiState<List<FundTransaction>>>(DataUiState.Loading)
    val fetchFundTransactionsState = _fetchFundTransactionsState.asStateFlow()


    fun fetchFunds(userId: String) {
        viewModelScope.launch {
            _fundsFetchState.value = DataUiState.Loading
            try {
                val result = firestoreRepository.getFirstGradeSubcollectionFund(userId, "funds")
                if (result.isSuccess) {
                    _fundsFetchState.value = DataUiState.Success(result.getOrThrow())
                } else {
                    _fundsFetchState.value = DataUiState.Error(Exception("Failed to fetch funds"))
                }
            } catch (e: Exception) {
                _fundsFetchState.value = DataUiState.Error(e)
            }
        }
    }

    fun fetchOneFund(userId: String, fundName: String){
        viewModelScope.launch {
            _fetchOneFundState.value = DataUiState.Loading
            try {
                val result = firestoreRepository.getOneFund(userId, fundName)
                if (result.isSuccess) {
                    _fetchOneFundState.value = DataUiState.Success(result.getOrThrow())
                } else {
                    _fetchOneFundState.value = DataUiState.Error(Exception("Failed to fetch fund"))
                }
            } catch (e: Exception) {
                _fetchOneFundState.value = DataUiState.Error(e)
            }
        }
    }

    fun updateFund(userId: String, fundName: String, fundData: FundData) {
        viewModelScope.launch {
            try {
                _addFundsState.value = DataUiState.Loading
                firestoreRepository.updateFund(userId, "funds", fundName, fundData)
                _addFundsState.value = DataUiState.Success(Unit)
                fetchFunds(userId)
            } catch (e: Exception) {
                _addFundsState.value = DataUiState.Error(e)
            }
        }

    }

    fun addFund(userId: String, fundName: String) {
        viewModelScope.launch {
            try {
                _addFundsState.value = DataUiState.Loading
                firestoreRepository.addFirstGradeSubcollectionFund(userId, "funds", FundData(id = fundName, name = fundName))
                _addFundsState.value = DataUiState.Success(Unit)
                fetchFunds(userId)
            } catch (e: Exception) {
                _addFundsState.value = DataUiState.Error(e)
            }
        }
    }

    fun deleteFund(userId: String, fundName: String) {
        viewModelScope.launch {
            try {
                firestoreRepository.deleteFirstGradeSubcollection(userId, "funds", fundName)
                fetchFunds(userId)
            } catch (e: Exception) {
                _addFundsState.value = DataUiState.Error(e)
            }
        }
    }

    fun addFundTransaction(userId: String, fundName: String, transactionType: String, transactionAmount: Double, transactionDate: String) {
        viewModelScope.launch {
            try {
                val time = transactionDate.split("-")

                _addFundsState.value = DataUiState.Loading
                val transaction = FundTransaction(
                    amount = transactionAmount,
                    date = time[1].slice(0..2) + "-" + time[2],
                    type = transactionType,
                    month = time[1].slice(0..2),
                    year = time[2].toInt(),
                    day = time[0].toInt()
                )
                firestoreRepository.addFundTransaction(userId, fundName, transaction)
                _addFundsState.value = DataUiState.Success(Unit)
                fetchFunds(userId)
            } catch (e: Exception) {
                _addFundsState.value = DataUiState.Error(e)
            }
        }
    }

    fun fetchFundTransactions(userId: String, fundName: String) {
        viewModelScope.launch {
            try {
                _fetchFundTransactionsState.value = DataUiState.Loading
                val result = firestoreRepository.fetchFundTransactions(userId, fundName)
                if (result.isSuccess) {
                    _fetchFundTransactionsState.value = DataUiState.Success(result.getOrThrow())
                } else {
                    _fetchFundTransactionsState.value = DataUiState.Error(Exception("Failed to fetch transactions"))
                }
            } catch (e: Exception) {
                _fetchFundTransactionsState.value = DataUiState.Error(e)
            }
        }
    }
}
