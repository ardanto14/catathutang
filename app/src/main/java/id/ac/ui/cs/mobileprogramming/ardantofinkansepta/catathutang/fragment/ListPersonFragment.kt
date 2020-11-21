package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment

import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.CreatePersonActivity
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.RequestCode

package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.adapter.PersonAdapter
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.broadcastreceiver.BatteryLowBroadcastReceiver
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.PersonViewModel
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.TransactionViewModel

class ListPersonFragment : Fragment() {

    private val database by lazy { MainRoomDatabase.getDatabase(activity) }
    private val personRepository by lazy { PersonRepository(database.personDao()) }
    private val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    private val personViewModel by lazy { PersonViewModel(personRepository) }
    private val transactionViewModel by lazy { TransactionViewModel(transactionRepository, personRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.create_person)
        button.setOnClickListener {
            val intent = Intent(this, CreatePersonActivity::class.java)
            startActivityForResult(intent, RequestCode.NEW_PERSON_ACTIVITY_REQUEST_CODE)
        }

        // Lookup the recyclerview in activity layout
        val personLists = findViewById<View>(R.id.person_list) as RecyclerView
        // Create adapter passing in the sample user data
        val adapter = PersonAdapter(this)
        // Attach the adapter to the recyclerview to populate items
        personLists.adapter = adapter
        // Set layout manager to position the items
        personLists.layoutManager = LinearLayoutManager(this)
        // That's all!

        personViewModel.allPerson.observe(this, Observer { persons ->
            // Update the cached copy of the words in the adapter.
            persons.let { adapter.setAllPerson(it) }
        })

        val br: BatteryLowBroadcastReceiver = BatteryLowBroadcastReceiver()

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(br, filter)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == RequestCode.NEW_PERSON_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val data = intentData?.getSerializableExtra("data") as HashMap<String, Any>

                val person = Person(name= data["name"] as String, initialValue = data["initialValue"] as Int, value = data["value"] as Int)
                personViewModel.insert(person)

                Toast.makeText(
                        applicationContext,
                        R.string.save_person_success,
                        Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                        applicationContext,
                        R.string.save_person_failed,
                        Toast.LENGTH_LONG
                ).show()
            }
        }

        if (requestCode == RequestCode.NEW_TRANSACTION_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                val data = intentData?.getSerializableExtra("data") as HashMap<String, Any>

                val newTransaction = Transaction(note=data["note"] as String, value=data["value"] as Int, userId=data["userId"] as Int)

                transactionViewModel.insert(newTransaction)

                Toast.makeText(
                        applicationContext,
                        R.string.add_transaction_success,
                        Toast.LENGTH_LONG
                ).show()

            } else {
                Toast.makeText(
                        applicationContext,
                        R.string.add_transaction_failed,
                        Toast.LENGTH_LONG
                ).show()
            }
        }

    }

}
