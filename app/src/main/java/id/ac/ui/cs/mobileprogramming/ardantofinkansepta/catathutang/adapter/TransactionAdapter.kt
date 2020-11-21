package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonWithTransactionsRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.ListTransactionViewModel

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private var transactionsList : List<Transaction> = ArrayList()

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val valueEditText = itemView.findViewById<TextView>(R.id.value)
        val noteEditText = itemView.findViewById<TextView>(R.id.note)
        val deleteButton = itemView.findViewById<Button>(R.id.delete_transaction)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val transactionView = inflater.inflate(R.layout.transaction, parent, false)
        // Return a new holder instance
        return ViewHolder(transactionView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: TransactionAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val transaction: Transaction = transactionsList.get(position)
        // Set item views based on your views and data model
        val valueEditText = viewHolder.valueEditText
        val noteEditText = viewHolder.noteEditText
        val deleteButton = viewHolder.deleteButton

        valueEditText.setText(transaction.value.toString())
        noteEditText.setText(transaction.note)

        deleteButton.setOnClickListener {
            val database by lazy { MainRoomDatabase.getDatabase(it.context) }
            val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
            val personWithTransactionsRepository by lazy { PersonWithTransactionsRepository(database.personWithTransactionsDao()) }
            val personRepository by lazy { PersonRepository(database.personDao()) }
            val listTransactionViewModel by lazy { ListTransactionViewModel(personWithTransactionsRepository, transactionRepository, personRepository) }

            val removedItem: Transaction = transactionsList[position]

            listTransactionViewModel.delete(removedItem)

            notifyItemRemoved(position);

            Toast.makeText(
                it.context,
                R.string.delete_person_success,
                Toast.LENGTH_LONG
            ).show()

        }

    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return transactionsList.size
    }

    fun setPersonWithTransactions(inp: List<Transaction>) {
        transactionsList = inp
        notifyDataSetChanged()
    }
}