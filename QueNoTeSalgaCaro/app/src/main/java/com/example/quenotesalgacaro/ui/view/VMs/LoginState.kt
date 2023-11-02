package com.example.quenotesalgacaro.ui.view.VMs

import com.google.firebase.auth.FirebaseUser

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val user: FirebaseUser) : LoginState()
    data class Error(val message: String) : LoginState()
}