import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.quenotesalgacaro.data.networking.Wallet

class WalletViewModel(private val firestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()) : ViewModel() {

    private val _walletsFetchState = MutableStateFlow<UiState<List<Wallet>>>(UiState.Loading)
    val walletsFetchState = _walletsFetchState.asStateFlow()

    private val _addWalletState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val addWalletState = _addWalletState.asStateFlow()

    fun fetchWallets(userId: String) {
        viewModelScope.launch {
            _walletsFetchState.value = UiState.Loading
            try {
                val result = firestoreRepository.getSubcollection(userId, "wallets")
                if (result.isSuccess) {
                    _walletsFetchState.value = UiState.Success(result.getOrThrow())
                } else {
                    _walletsFetchState.value = UiState.Error(Exception("Failed to fetch wallets"))
                }
            } catch (e: Exception) {
                _walletsFetchState.value = UiState.Error(e)
            }
        }
    }


    fun addWallet(userId: String, walletName: String) {
        viewModelScope.launch {
            try {
                _addWalletState.value = UiState.Loading
                firestoreRepository.addFirstGradeSubcollection(userId, walletName, "wallets")
                _addWalletState.value = UiState.Success(Unit)
                fetchWallets(userId)
            } catch (e: Exception) {
                _addWalletState.value = UiState.Error(e)
            }
        }
    }
}
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val exception: Throwable) : UiState<Nothing>()
}

