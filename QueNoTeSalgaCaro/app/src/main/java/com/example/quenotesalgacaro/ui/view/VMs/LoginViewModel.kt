package com.example.quenotesalgacaro.ui.view.VMs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quenotesalgacaro.data.repository.FireBaseAuthRepository

import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel (private val authRepository: FireBaseAuthRepository = FireBaseAuthRepository()): ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    fun login(username: String, password: String) {
        _loginState.value = LoginState.Loading

        authRepository.signIn(username, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _loginState.value = LoginState.Success(task.result?.user!!)
            } else {
                _loginState.value = LoginState.Error(task.exception?.message ?: "Unknown Error")
            }
        }
    }

}