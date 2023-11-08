package com.example.quenotesalgacaro.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirebaseAuthRepository : AuthRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun registerNewUser(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                authResult.user
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun signIn(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                authResult.user
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun signOut() {
        return withContext(Dispatchers.IO) {
            try {
                firebaseAuth.signOut()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun deleteUser() {
        return withContext(Dispatchers.IO) {
            try {
                firebaseAuth.currentUser?.delete()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }


}