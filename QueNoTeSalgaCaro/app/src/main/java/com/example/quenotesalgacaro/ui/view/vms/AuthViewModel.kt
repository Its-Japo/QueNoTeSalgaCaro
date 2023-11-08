package com.example.quenotesalgacaro.ui.view.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.repository.AuthRepository
import com.example.quenotesalgacaro.data.repository.DataBaseRepository
import com.example.quenotesalgacaro.data.repository.FirebaseAuthRepository
import com.example.quenotesalgacaro.data.repository.FirebaseFirestoreRepository
import com.example.quenotesalgacaro.ui.view.uistates.LoginUiState
import com.example.quenotesalgacaro.ui.view.uistates.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class AuthViewModel(
    private val firebaseAuthRepository: AuthRepository = FirebaseAuthRepository(),
    private val firebaseFirestoreRepository: DataBaseRepository = FirebaseFirestoreRepository()
) : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState: MutableStateFlow<LoginUiState> = _loginUiState

    private val _registerUiState = MutableStateFlow<RegisterUiState>(RegisterUiState())
    val registerUiState: MutableStateFlow<RegisterUiState> = _registerUiState

    init {
        val user = firebaseAuthRepository.getCurrentUser()
        if (user != null) {
            _loginUiState.value = LoginUiState(user = user, success = true)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.value = LoginUiState(loading = true)
            try {
                val user = firebaseAuthRepository.signIn(email, password)
                _loginUiState.value = LoginUiState(success = user != null, user = user)
            } catch (e: Exception) {
                _loginUiState.value = LoginUiState(error = e.localizedMessage ?: "Login failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            firebaseAuthRepository.signOut()
            _loginUiState.value = LoginUiState()
        }
    }

    fun deleteUser() {
        _loginUiState.value = LoginUiState()
        viewModelScope.launch {
            firebaseFirestoreRepository.deleteUser(
                firebaseAuthRepository.getCurrentUser()
            )
            firebaseAuthRepository.deleteUser()
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerUiState.value = RegisterUiState(loading = true)
            try {
                val user = firebaseAuthRepository.registerNewUser(email, password)
                _registerUiState.value = RegisterUiState(success = user != null, user = user)
                if (user != null) {
                    firebaseFirestoreRepository.registerNewUser(user, password)
                }
                _registerUiState.value = RegisterUiState()

            } catch (e: Exception) {
                _registerUiState.value = RegisterUiState(error = e.localizedMessage ?: "Registration failed")
            }
        }
    }
}