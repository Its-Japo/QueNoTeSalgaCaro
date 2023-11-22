package com.example.quenotesalgacaro.data.repository

import com.example.quenotesalgacaro.data.networking.BudgetConfiguration
import com.example.quenotesalgacaro.data.networking.FundData
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.data.networking.Transaction
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseFirestoreRepository: DataBaseRepository {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun registerNewUser(user: FirebaseUser?) {
        return withContext(Dispatchers.IO) {
            try {
                val userMap = hashMapOf(
                    "email" to user?.email,
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

    override suspend fun addFirstGradeSubcollectionFund(uid: String?, collectionName: String, document: FundData): Result<Unit>  {
        return withContext(Dispatchers.IO) {
            try {
                firebaseFirestore.collection("users").document(uid?: "")
                    .collection(collectionName).document(document.id)
                    .set(hashMapOf(
                        "name" to document.name,
                        "initialcapital" to document.initialCapital,
                        "interest" to document.interest,
                        "anualcapitalizations" to document.anualcapitalizations,
                        "goal" to document.goal
                    )).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getOneFund(uid: String, fundName: String): Result<FundData> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = firebaseFirestore.collection("users").document(uid)
                    .collection("funds").document(fundName).get().await()
                val fund = snapshot.toObject(FundData::class.java)
                Result.success(fund!!)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun updateFund(uid: String, collectionName: String, fundName: String, data: FundData): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                firebaseFirestore.collection("users").document(uid)
                    .collection(collectionName).document(fundName)
                    .update("name", data.name,
                        "initialcapital", data.initialCapital,
                        "interest", data.interest,
                        "anualcapitalizations", data.anualcapitalizations,
                        "goal", data.goal).await()
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

    override suspend fun getFirstGradeSubcollectionFund(uid: String, collectionName: String): Result<List<FundData>> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = firebaseFirestore.collection("users").document(uid)
                    .collection(collectionName).get().await()
                val wallets = snapshot.documents.mapNotNull { it.toObject(FundData::class.java) }
                Result.success(wallets)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getSecondGradeSubcollectionBudget(uid: String, collectionName: String, entity: String, subcollectionName: String): Result<List<BudgetConfiguration>> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = firebaseFirestore.collection("users").document(uid)
                    .collection(collectionName).document(entity)
                    .collection(subcollectionName).get().await()
                val budgets = snapshot.documents.mapNotNull { documentSnapshot ->
                    val budget = documentSnapshot.toObject(BudgetConfiguration::class.java)
                    if (budget != null) {
                        budget.id = documentSnapshot.id
                    }
                    budget
                }
                Result.success(budgets)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getSecondGradeSubcollectionWallet(uid: String, collectionName: String, entity: String, subcollectionName: String): Result<List<SimpleDocument>> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = firebaseFirestore.collection("users").document(uid)
                    .collection(collectionName).document(entity)
                    .collection(subcollectionName).get().await()
                val budgets = snapshot.documents.mapNotNull { documentSnapshot ->
                    val budget = documentSnapshot.toObject(SimpleDocument::class.java)
                    if (budget != null) {
                        budget.id = documentSnapshot.id
                    }
                    budget
                }
                Result.success(budgets)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun deleteSecondGradeSubcollectionDocument(uid: String?, collectionName: String, entity: String, subcollectionName: String, documentName: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                if (uid != null) {
                    firebaseFirestore.collection("users").document(uid)
                        .collection(collectionName).document(entity)
                        .collection(subcollectionName).document(documentName)
                        .delete().await()
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun addSecondGradeSubcollectionDocumentBudget(uid: String?, collectionName: String, entity: String, subcollectionName: String, budgetConfiguration: BudgetConfiguration): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val budgetMap = hashMapOf(
                    "amount" to budgetConfiguration.amount,
                    "concept" to budgetConfiguration.concept
                )

                if (uid != null) {
                    firebaseFirestore.collection("users").document(uid)
                        .collection(collectionName).document(entity)
                        .collection(subcollectionName).document()
                        .set(budgetMap).await()
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun addSecondGradeSubcollectionDocumentWallet(uid: String?, collectionName: String, entity: String, subcollectionName: String, data: SimpleDocument): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val documentMap = hashMapOf(
                    "name" to data.name,
                )

                if (uid != null) {
                    firebaseFirestore.collection("users").document(uid)
                        .collection(collectionName).document(entity)
                        .collection(subcollectionName).document()
                        .set(documentMap).await()
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun addSecondGradeSubcollectionDocumentTransaction(uid: String?, collectionName: String, entity: String, subcollectionName: String, data: Transaction): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val documentMap = hashMapOf(
                    "amount" to data.amount,
                    "category" to data.category,
                    "date" to data.date,
                    "day" to data.day,
                    "description" to data.description,
                    "month" to data.month,
                    "year" to data.year
                )
                if (uid != null) {
                    firebaseFirestore.collection("users").document(uid)
                        .collection(collectionName).document(entity)
                        .collection(subcollectionName).document()
                        .set(documentMap).await()
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun fetchTransactions (uid: String?, walletName: String, date: String): Result<List<Transaction>> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = firebaseFirestore.collection("users").document(uid ?: "")
                    .collection("wallets").document(walletName)
                    .collection("transactions").whereEqualTo("date", date).get().await()
                val transactions = snapshot.documents.mapNotNull { documentSnapshot ->
                    val transaction = documentSnapshot.toObject(Transaction::class.java)
                    if (transaction != null) {
                        transaction.id = documentSnapshot.id
                    }
                    transaction
                }
                Result.success(transactions)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun fetchTransactionsYear (uid: String?, walletName: String, date: Int): Result<List<Transaction>> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = firebaseFirestore.collection("users").document(uid ?: "")
                    .collection("wallets").document(walletName)
                    .collection("transactions").whereEqualTo("year", date).get().await()
                val transactions = snapshot.documents.mapNotNull { documentSnapshot ->
                    val transaction = documentSnapshot.toObject(Transaction::class.java)
                    if (transaction != null) {
                        transaction.id = documentSnapshot.id
                    }
                    transaction
                }
                Result.success(transactions)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun updateDocument(uid: String?, collectionName: String, documentId: String, fieldName: String, element: Any): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                if (uid != null) {
                    firebaseFirestore.collection("users").document(uid)
                        .collection(collectionName).document(documentId)
                        .update(fieldName, element).await()
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }

    override suspend fun deleteFirstGradeSubcollection(uid: String?, subcollection: String, subcollectionName: String) : Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                if (uid != null) {
                    val documents = firebaseFirestore.collection("users").document(uid)
                        .collection(subcollection).document(subcollectionName).delete().await()
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }



}