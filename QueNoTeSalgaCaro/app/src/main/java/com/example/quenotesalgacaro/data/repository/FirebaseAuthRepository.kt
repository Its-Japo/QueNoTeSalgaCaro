package com.example.quenotesalgacaro.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirebaseAuthRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun registerNewUser(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                authResult.user
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                authResult.user
            } catch (e: Exception) {
                throw e
            }
        }
    }
}