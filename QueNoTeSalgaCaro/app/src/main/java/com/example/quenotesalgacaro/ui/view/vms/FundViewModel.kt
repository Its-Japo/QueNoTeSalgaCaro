import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.data.repository.DataBaseRepository
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FundViewModel(private val firestoreRepository: DataBaseRepository = FirebaseFirestoreRepository()) : ViewModel() {

    private val _fundsFetchState = MutableStateFlow<DataUiState<List<SimpleDocument>>>(DataUiState.Loading)
    val fundFetchState = _fundsFetchState.asStateFlow()

    private val _addFundsState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Loading)
    val addFundState = _addFundsState.asStateFlow()

    fun fetchFunds(userId: String) {
        viewModelScope.launch {
            _fundsFetchState.value = DataUiState.Loading
            try {
                val result = firestoreRepository.getFirstGradeSubcollection(userId, "funds")
                if (result.isSuccess) {
                    _fundsFetchState.value = DataUiState.Success(result.getOrThrow())
                } else {
                    _fundsFetchState.value = DataUiState.Error(Exception("Failed to fetch budgets"))
                }
            } catch (e: Exception) {
                _fundsFetchState.value = DataUiState.Error(e)
            }
        }
    }


    fun addFund(userId: String, walletName: String) {
        viewModelScope.launch {
            try {
                _addFundsState.value = DataUiState.Loading
                firestoreRepository.addFirstGradeSubcollection(userId, walletName, "funds")
                _addFundsState.value = DataUiState.Success(Unit)
                fetchFunds(userId)
            } catch (e: Exception) {
                _addFundsState.value = DataUiState.Error(e)
            }
        }
    }
}
