import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.quenotesalgacaro.data.networking.Wallet

class FundViewModel(private val firestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()) : ViewModel() {

    private val _fundsFetchState = MutableStateFlow<UiState<List<Wallet>>>(UiState.Loading)
    val fundFetchState = _fundsFetchState.asStateFlow()

    private val _addFundsState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val addFundState = _addFundsState.asStateFlow()

    fun fetchFunds(userId: String) {
        viewModelScope.launch {
            _fundsFetchState.value = UiState.Loading
            try {
                val result = firestoreRepository.getSubcollection(userId, "funds")
                if (result.isSuccess) {
                    _fundsFetchState.value = UiState.Success(result.getOrThrow())
                } else {
                    _fundsFetchState.value = UiState.Error(Exception("Failed to fetch budgets"))
                }
            } catch (e: Exception) {
                _fundsFetchState.value = UiState.Error(e)
            }
        }
    }


    fun addFund(userId: String, walletName: String) {
        viewModelScope.launch {
            try {
                _addFundsState.value = UiState.Loading
                firestoreRepository.addFirstGradeSubcollection(userId, walletName, "funds")
                _addFundsState.value = UiState.Success(Unit)
                fetchFunds(userId)
            } catch (e: Exception) {
                _addFundsState.value = UiState.Error(e)
            }
        }
    }
}
