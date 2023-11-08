import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.data.repository.DataBaseRepository
import com.example.quenotesalgacaro.ui.view.uistates.DataUiState

class WalletViewModel(private val firestoreRepository: DataBaseRepository = FirebaseFirestoreRepository()) : ViewModel() {

    private val _walletsFetchState = MutableStateFlow<DataUiState<List<SimpleDocument>>>(DataUiState.Loading)
    val walletsFetchState = _walletsFetchState.asStateFlow()

    private val _addWalletState = MutableStateFlow<DataUiState<Unit>>(DataUiState.Loading)
    val addWalletState = _addWalletState.asStateFlow()

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
}
