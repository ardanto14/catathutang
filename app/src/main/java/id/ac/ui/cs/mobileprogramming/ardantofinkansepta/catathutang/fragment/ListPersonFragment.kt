package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment


import android.R.attr.key
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.adapter.PersonAdapter
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
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

}
