package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.*
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment.CreatePersonFragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment.CreateTransactionFragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment.ListTransactionFragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.viewmodel.PersonViewModel


// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class PersonAdapter(private val activity: FragmentActivity) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    private var personList: List<Person> = ArrayList()

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView = itemView.findViewById<TextView>(R.id.name)
        val valueTextView = itemView.findViewById<TextView>(R.id.value)
        val deletePersonButton = itemView.findViewById<Button>(R.id.delete_person)
        val addTransactionButton = itemView.findViewById<Button>(R.id.add_transaction)
        val seeTransacionButton = itemView.findViewById<Button>(R.id.see_transaction)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val personView = inflater.inflate(R.layout.person, parent, false)
        // Return a new holder instance
        return ViewHolder(personView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: PersonAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val person: Person = personList.get(position)
        // Set item views based on your views and data model
        val textView = viewHolder.nameTextView
        val valueTextView = viewHolder.valueTextView
        val deletePersonButton = viewHolder.deletePersonButton
        val addTransactionButton = viewHolder.addTransactionButton
        val seeTransactionButton = viewHolder.seeTransacionButton

        textView.setText(person.name)
        valueTextView.setText(person.value.toString())

        deletePersonButton.setOnClickListener {
            val database by lazy { MainRoomDatabase.getDatabase(it.context) }
            val repository by lazy { PersonRepository(database.personDao()) }
            val personViewModel by lazy { PersonViewModel(repository) }

            val removedItem: Person = personList[position]

            personViewModel.delete(removedItem)


            notifyItemRemoved(position);

            Toast.makeText(
                it.context,
                R.string.delete_person_success,
                Toast.LENGTH_LONG
            ).show()

        }

        addTransactionButton.setOnClickListener {
            val newFragment = CreateTransactionFragment()

            val bundle = Bundle()
            bundle.putInt("userId", person.id);
            newFragment.arguments = bundle

            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.placeholder, newFragment)
                    .addToBackStack(null)
                    .commit();

        }

        seeTransactionButton.setOnClickListener {
            val newFragment = ListTransactionFragment()

            val bundle = Bundle()
            bundle.putInt("userId", person.id);
            newFragment.arguments = bundle

            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.placeholder, newFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return personList.size
    }

    fun setAllPerson(all: List<Person>) {
        personList = all
        notifyDataSetChanged()
    }
}