package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class TransactionViewModel(private val repository: TransactionRepository, private val personRepository: PersonRepository) : ViewModel() {

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    val allTransaction: LiveData<List<Transaction>> = repository.allTransaction.asLiveData()



    fun insert(transaction: Transaction) = viewModelScope.launch {

        repository.insert(transaction)

        personRepository.updateValue(transaction.userId, transaction.value)

    }
}