package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository

import androidx.annotation.WorkerThread
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.PersonWithTransactionsDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.PersonWithTransactions
import kotlinx.coroutines.flow.Flow


class PersonWithTransactionsRepository(private val personWithTransactionsDao: PersonWithTransactionsDao) {

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    fun getPersonWithTransactionsById(id: Int) : Flow<PersonWithTransactions> {
        return personWithTransactionsDao.getPersonWithTranscationsById(id)
    }

}