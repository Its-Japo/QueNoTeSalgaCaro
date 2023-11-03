package com.example.quenotesalgacaro.ui.view.VMs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quenotesalgacaro.data.repository.FireBaseAuthRepository
import com.example.quenotesalgacaro.ui.view.UiStates.LoginUiState
import com.example.quenotesalgacaro.ui.view.UiStates.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class AuthViewModel(private val firebaseAuthRepository: FireBaseAuthRepository = FireBaseAuthRepository()) : ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState: MutableStateFlow<LoginUiState> = _loginUiState

    private val _registerUiState = MutableStateFlow<RegisterUiState>(RegisterUiState())
    val registerUiState: MutableStateFlow<RegisterUiState> = _registerUiState

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

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerUiState.value = RegisterUiState(loading = true)
            try {
                val user = firebaseAuthRepository.registerNewUser(email, password)
                _registerUiState.value = RegisterUiState(success = user != null, user = user)


            } catch (e: Exception) {
                _registerUiState.value = RegisterUiState(error = e.localizedMessage ?: "Registration failed")
            }
        }
    }
}