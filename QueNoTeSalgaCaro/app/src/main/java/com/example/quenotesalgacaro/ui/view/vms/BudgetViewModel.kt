import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.quenotesalgacaro.data.networking.Wallet

class BudgetViewModel(private val firestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()) : ViewModel() {

    private val _budgetsFetchState = MutableStateFlow<UiState<List<Wallet>>>(UiState.Loading)
    val budgetsFetchState = _budgetsFetchState.asStateFlow()

    private val _addBudgetState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val addBudgetState = _addBudgetState.asStateFlow()

    fun fetchBudgets(userId: String) {
        viewModelScope.launch {
            _budgetsFetchState.value = UiState.Loading
            try {
                val result = firestoreRepository.getSubcollection(userId, "budgets")
                if (result.isSuccess) {
                    _budgetsFetchState.value = UiState.Success(result.getOrThrow())
                } else {
                    _budgetsFetchState.value = UiState.Error(Exception("Failed to fetch budgets"))
                }
            } catch (e: Exception) {
                _budgetsFetchState.value = UiState.Error(e)
            }
        }
    }


    fun addBudget(userId: String, walletName: String) {
        viewModelScope.launch {
            try {
                _addBudgetState.value = UiState.Loading
                firestoreRepository.addFirstGradeSubcollection(userId, walletName, "budgets")
                _addBudgetState.value = UiState.Success(Unit)
                fetchBudgets(userId)
            } catch (e: Exception) {
                _addBudgetState.value = UiState.Error(e)
            }
        }
    }
}
