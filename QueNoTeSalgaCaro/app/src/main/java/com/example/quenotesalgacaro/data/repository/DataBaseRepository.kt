package com.example.quenotesalgacaro.data.repository

import com.example.quenotesalgacaro.data.networking.BudgetConfiguration
import com.example.quenotesalgacaro.data.networking.FundData
import com.example.quenotesalgacaro.data.networking.FundTransaction
import com.example.quenotesalgacaro.data.networking.SimpleDocument
import com.example.quenotesalgacaro.data.networking.Transaction
import com.google.firebase.auth.FirebaseUser

interface DataBaseRepository {

    suspend fun registerNewUser(user: FirebaseUser?)
    suspend fun deleteUser(user: FirebaseUser?)
    suspend fun getFirstGradeSubcollection(uid: String, collectionName: String): Result<List<SimpleDocument>>
    suspend fun getFirstGradeSubcollectionFund(uid: String, collectionName: String): Result<List<FundData>>
    suspend fun addFirstGradeSubcollection(uid: String?, name: String, collectionName: String): Result<Unit>
    suspend fun addFirstGradeSubcollectionFund(uid: String?, collectionName: String, document: FundData): Result<Unit>
    suspend fun getOneFund(uid: String, fundName: String): Result<FundData>
    suspend fun updateFund(uid: String, collectionName: String, fundName: String, data: FundData): Result<Unit>
    suspend fun getSecondGradeSubcollectionBudget(uid: String, collectionName: String, entity: String, subcollectionName: String): Result<List<BudgetConfiguration>>
    suspend fun getSecondGradeSubcollectionWallet(uid: String, collectionName: String, entity: String, subcollectionName: String): Result<List<SimpleDocument>>
    suspend fun deleteSecondGradeSubcollectionDocument(uid: String?, collectionName: String, entity: String, subcollectionName: String, documentName: String): Result<Unit>
    suspend fun addSecondGradeSubcollectionDocumentBudget(uid: String?, collectionName: String, entity: String, subcollectionName: String, budgetConfiguration: BudgetConfiguration): Result<Unit>
    suspend fun addSecondGradeSubcollectionDocumentWallet(uid: String?, collectionName: String, entity: String, subcollectionName: String, data: SimpleDocument): Result<Unit>
    suspend fun addSecondGradeSubcollectionDocumentTransaction(uid: String?, collectionName: String, entity: String, subcollectionName: String, data: Transaction): Result<Unit>
    suspend fun fetchTransactionsYear (uid: String?, walletName: String, date: Int): Result<List<Transaction>>
    suspend fun fetchTransactions (uid: String?, walletName: String, date: String): Result<List<Transaction>>
    suspend fun updateDocument(uid: String?, collectionName: String, documentId: String, fieldName: String, element: Any): Result<Unit>
    suspend fun deleteFirstGradeSubcollection(uid: String?, subcollection: String, subcollectionName: String) : Result<Unit>
    suspend fun addFundTransaction(uid: String?, fundName: String, transaction: FundTransaction): Result<Unit>
    suspend fun fetchFundTransactions(uid: String, fundName: String): Result<List<FundTransaction>>
    suspend fun deleteTransaction(uid: String, type: String, name: String, documentId: String): Result<Unit>
}