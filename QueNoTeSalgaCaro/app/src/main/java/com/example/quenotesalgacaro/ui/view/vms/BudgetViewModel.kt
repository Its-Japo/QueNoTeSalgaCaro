import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.networking.BudgetConfiguration
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.data.repository.DataBaseRepository
import com.example.quenotesalgacaro.ui.view.uistates.BudgetConfigurationStruct
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState

class BudgetViewModel(
    private val firestoreRepository: DataBaseRepository = FirebaseFirestoreRepository()
): ViewModel() {

    private val _budgetsFetchState = MutableStateFlow<DataUiState<List<SimpleDocument>>>(DataUiState.Loading)
    val budgetsFetchState = _budgetsFetchState.asStateFlow()

    private val _addBudgetState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Loading)
    val addBudgetState = _addBudgetState.asStateFlow()

    private val _budgetConfigurationFetchState = MutableStateFlow<DataUiState<BudgetConfigurationStruct>>(DataUiState.Loading)
    val budgetConfigurationFetchState = _budgetConfigurationFetchState.asStateFlow()

    fun fetchBudgets(userId: String) {
        viewModelScope.launch {
            _budgetsFetchState.value = DataUiState.Loading
            try {
                val result = firestoreRepository.getFirstGradeSubcollection(userId, "budgets")
                if (result.isSuccess) {
                    _budgetsFetchState.value = DataUiState.Success(result.getOrThrow())
                } else {
                    _budgetsFetchState.value = DataUiState.Error(Exception("Failed to fetch budgets"))
                }
            } catch (e: Exception) {
                _budgetsFetchState.value = DataUiState.Error(e)
            }
        }
    }

    fun fetchBudgetConfiguration(userId: String, budgetName: String) {
        viewModelScope.launch {
            _budgetConfigurationFetchState.value = DataUiState.Loading
            var configuration: BudgetConfigurationStruct = BudgetConfigurationStruct()
            try {

                val income = firestoreRepository.getSecondGradeSubcollection(userId, "budgets", budgetName, "income")
                if (income.isSuccess) {
                    configuration = configuration.copy(income = income.getOrThrow())
                }
                val fixedCosts = firestoreRepository.getSecondGradeSubcollection(userId, "budgets", budgetName, "fixedExpenses")
                if (fixedCosts.isSuccess) {
                    configuration = configuration.copy(fixedExpenses = fixedCosts.getOrThrow())
                }
                val variableCosts = firestoreRepository.getSecondGradeSubcollection(userId, "budgets", budgetName, "variableExpenses")
                if (variableCosts.isSuccess) {
                    configuration = configuration.copy(variableExpenses = variableCosts.getOrThrow())
                }

                _budgetConfigurationFetchState.value = DataUiState.Success(configuration)

            } catch (e: Exception) {
                _budgetConfigurationFetchState.value = DataUiState.Error(e)
            }
        }
    }


    fun addBudget(userId: String, walletName: String) {
        viewModelScope.launch {
            try {
                _addBudgetState.value = DataUiState.Loading
                firestoreRepository.addFirstGradeSubcollection(userId, walletName, "budgets")
                _addBudgetState.value = DataUiState.Success(Unit)
                fetchBudgets(userId)
            } catch (e: Exception) {
                _addBudgetState.value = DataUiState.Error(e)
            }
        }
    }
}
