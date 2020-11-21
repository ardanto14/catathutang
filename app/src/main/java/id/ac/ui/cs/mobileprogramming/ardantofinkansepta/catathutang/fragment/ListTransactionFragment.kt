package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.adapter.TransactionAdapter
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonWithTransactionsRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.ListTransactionViewModel

class ListTransactionFragment : Fragment() {

    private val database by lazy { MainRoomDatabase.getDatabase(activity!!) }
    private val personWithTransactionsRepository by lazy { PersonWithTransactionsRepository(database.personWithTransactionsDao()) }
    private val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    private val personRepository by lazy { PersonRepository(database.personDao()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ) : View {

        val mView: View = inflater.inflate(R.layout.transaction_list, container, false)

        val userId = arguments?.getInt("userId", -1)

        if (userId == -1 || userId == null) {

            activity!!.supportFragmentManager.popBackStack()


        }

        // Lookup the recyclerview in activity layout
        val transactionList = mView.findViewById<View>(R.id.transaction_list) as RecyclerView
        // Create adapter passing in the sample user data
        val adapter = TransactionAdapter()
        // Attach the adapter to the recyclerview to populate items
        transactionList.adapter = adapter
        // Set layout manager to position the items
        transactionList.layoutManager = LinearLayoutManager(activity!!)
        // That's all!

        val listTransactionViewModel = ListTransactionViewModel(personWithTransactionsRepository, transactionRepository, personRepository)
        listTransactionViewModel.getListTransactionById(userId!!).observe(this, Observer { transactions ->
            // Update the cached copy of the words in the adapter.
            transactions.let { adapter.setPersonWithTransactions(it.transactions) }
        })

        return mView
    }
}
