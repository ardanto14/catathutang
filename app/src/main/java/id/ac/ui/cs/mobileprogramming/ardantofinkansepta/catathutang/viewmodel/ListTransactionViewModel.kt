package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.PersonWithTransactionsDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.TransactionDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.PersonWithTransactions
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonWithTransactionsRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import kotlinx.coroutines.launch

class ListTransactionViewModel(private val personWithTransactionsRepository: PersonWithTransactionsRepository,
                               private val transactionRepository: TransactionRepository,
                               private val personRepository: PersonRepository) : ViewModel() {



    fun delete(transaction: Transaction) = viewModelScope.launch {
        transactionRepository.delete(transaction)

        personRepository.updateValue(transaction.userId, transaction.value * -1)
    }

    fun getListTransactionById(id: Int) : LiveData<PersonWithTransactions> {
        return personWithTransactionsRepository.getPersonWithTransactionsById(id).asLiveData()
    }
}