package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.asLiveData
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.PersonDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PersonRepository(private val personDao: PersonDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPerson: Flow<List<Person>> = personDao.getAllPerson()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insert(person: Person) {
        personDao.insert(person)
    }

    @WorkerThread
    suspend fun delete(person: Person) {
        personDao.delete(person)
    }

    @WorkerThread
    suspend fun updateValue(id: Int, value: Int) {

        personDao.updateValue(id, value)

    }
}