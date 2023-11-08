package com.example.quenotesalgacaro.data.repository

import com.example.quenotesalgacaro.data.networking.BudgetConfiguration
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseFirestoreRepository: DataBaseRepository {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun registerNewUser(user: FirebaseUser?, password: String) {
        return withContext(Dispatchers.IO) {
            try {
                val userMap = hashMapOf(
                    "email" to user?.email,
                    "password" to password
                )
                firebaseFirestore.collection("users").document(user?.uid ?: "")
                    .set(userMap).await()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun deleteUser(user: FirebaseUser?) {
        return withContext(Dispatchers.IO) {
            try {
                firebaseFirestore.collection("users").document(user?.uid ?: "")
                    .delete().await()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun addFirstGradeSubcollection(uid: String?, name: String, collectionName: String): Result<Unit>  {
        return withContext(Dispatchers.IO) {
            try {
                firebaseFirestore.collection("users").document(uid?: "")
                    .collection(collectionName).document(name)
                    .set(hashMapOf(
                        "name" to name
                    )).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }


    }

    override suspend fun getFirstGradeSubcollection(uid: String, collectionName: String): Result<List<SimpleDocument>> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = firebaseFirestore.collection("users").document(uid)
                    .collection(collectionName).get().await()
                val wallets = snapshot.documents.mapNotNull { it.toObject(SimpleDocument::class.java) }
                Result.success(wallets)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getSecondGradeSubcollection(uid: String, collectionName: String, entity: String, subcollectionName: String): Result<List<BudgetConfiguration>> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = firebaseFirestore.collection("users").document(uid)
                    .collection(collectionName).document(entity)
                    .collection(subcollectionName).get().await()
                val budgets = snapshot.documents.mapNotNull { it.toObject(BudgetConfiguration::class.java) }
                Result.success(budgets)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }



}