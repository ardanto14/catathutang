package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment.ListPersonFragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.PersonViewModel
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.TransactionViewModel


class MainActivity : AppCompatActivity() {

    private val database by lazy { MainRoomDatabase.getDatabase(this) }
    private val personRepository by lazy { PersonRepository(database.personDao()) }
    private val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    private val personViewModel by lazy { PersonViewModel(personRepository) }
    private val transactionViewModel by lazy { TransactionViewModel(transactionRepository, personRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // Begin the transaction

        // Begin the transaction
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

        ft.replace(R.id.placeholder, ListPersonFragment())

        ft.commit()

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

    fun newPerson(name: String, initialValue: Int, value: Int) {

        val person = Person(name= name, initialValue = initialValue, value = value)
        personViewModel.insert(person)

        Toast.makeText(
                applicationContext,
                R.string.save_person_success,
                Toast.LENGTH_LONG
        ).show()

    }

    fun newTransaction(note: String, value: Int, userId: Int) {

        val newTransaction = Transaction(note=note, value=value, userId=userId)

        transactionViewModel.insert(newTransaction)

        Toast.makeText(
                applicationContext,
                R.string.add_transaction_success,
                Toast.LENGTH_LONG
        ).show()
    }

}
