package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment


import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.RequestCode
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.adapter.PersonAdapter
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.PersonViewModel
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.TransactionViewModel


class ListPersonFragment : Fragment() {

    private val database by lazy { MainRoomDatabase.getDatabase(activity!!) }
    private val personRepository by lazy { PersonRepository(database.personDao()) }
    private val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    private val personViewModel by lazy { PersonViewModel(personRepository) }
    private val transactionViewModel by lazy { TransactionViewModel(transactionRepository, personRepository) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ) : View {

        val mView: View = inflater.inflate(R.layout.activity_main, container, false)


        val button = mView.findViewById<Button>(R.id.create_person)

        button.setOnClickListener {

            val newFragment = CreatePersonFragment()

            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.placeholder, newFragment)
                    .addToBackStack(null)
                    .commit();

        }

        val button2 = mView.findViewById<Button>(R.id.about)

        button2.setOnClickListener {

            val newFragment = AboutMeFragment()

            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.placeholder, newFragment)
                .addToBackStack(null)
                .commit();

        }

        val button3 = mView.findViewById<Button>(R.id.import_contact)

        button3.setOnClickListener {

            checkPermission(
                Manifest.permission.READ_CONTACTS,
                RequestCode.READ_CONTACT_REQUEST_CODE);

        }

        val button4 = mView.findViewById<Button>(R.id.backup)

        button4.setOnClickListener {

            val newFragment = BackupFragment()

            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.placeholder, newFragment)
                .addToBackStack(null)
                .commit();

        }

        // Lookup the recyclerview in activity layout
        val personLists = mView.findViewById<View>(R.id.person_list) as RecyclerView
        // Create adapter passing in the sample user data
        val adapter = PersonAdapter(activity!!)
        // Attach the adapter to the recyclerview to populate items
        personLists.adapter = adapter
        // Set layout manager to position the items
        personLists.layoutManager = LinearLayoutManager(activity!!)
        // That's all!

        personViewModel.allPerson.observe(this, Observer { persons ->
            // Update the cached copy of the words in the adapter.
            persons.let { adapter.setAllPerson(it) }
        })


        return mView

    }
    fun checkPermission(permission: String, requestCode: Int) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                activity!!,
                permission
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    activity!!, arrayOf(permission),
                    requestCode
                )
        } else {
            val cr = context!!.contentResolver
            val cur: Cursor? = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )

            if (cur!!.getCount() > 0) {
                while (cur!!.moveToNext()) {
                    val name: String = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    )

                    personViewModel.insert(Person(name=name, initialValue = 0, value = 0))
                }
            }
        }
    }




}
