package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository

import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.TransactionDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction

import androidx.annotation.WorkerThread
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.PersonDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TransactionRepository(private val transactionDao: TransactionDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTransaction: Flow<List<Transaction>> = transactionDao.getAllTransaction()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    @WorkerThread
    suspend fun delete(transaction: Transaction) {
        transactionDao.delete(transaction)
    }
}