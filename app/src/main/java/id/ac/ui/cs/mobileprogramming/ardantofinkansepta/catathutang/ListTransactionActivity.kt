package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.adapter.TransactionAdapter
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonWithTransactionsRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.ListTransactionViewModel

class ListTransactionActivity : AppCompatActivity() {

    private val database by lazy { MainRoomDatabase.getDatabase(this) }
    private val personWithTransactionsRepository by lazy { PersonWithTransactionsRepository(database.personWithTransactionsDao()) }
    private val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    private val personRepository by lazy { PersonRepository(database.personDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_list)

        val userId = intent.getIntExtra("userId", -1)

        if (userId == -1) {
            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_CANCELED, intent)

            finish()
        }

        // Lookup the recyclerview in activity layout
        val transactionList = findViewById<View>(R.id.transaction_list) as RecyclerView
        // Create adapter passing in the sample user data
        val adapter = TransactionAdapter()
        // Attach the adapter to the recyclerview to populate items
        transactionList.adapter = adapter
        // Set layout manager to position the items
        transactionList.layoutManager = LinearLayoutManager(this)
        // That's all!

        val listTransactionViewModel = ListTransactionViewModel(personWithTransactionsRepository, transactionRepository, personRepository)
        listTransactionViewModel.getListTransactionById(userId).observe(this, Observer { transactions ->
            // Update the cached copy of the words in the adapter.
            transactions.let { adapter.setPersonWithTransactions(it.transactions) }
        })
    }
}