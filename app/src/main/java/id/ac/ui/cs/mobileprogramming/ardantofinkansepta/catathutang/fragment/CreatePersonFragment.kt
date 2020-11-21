package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.MainActivity
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R


class CreatePersonFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ) : View {

        val mView: View = inflater.inflate(R.layout.create_person, container, false)

        var nameEditText = mView.findViewById(R.id.name) as EditText

        var initialValueEditText = mView.findViewById(R.id.initial_value) as EditText

        val button = mView.findViewById<Button>(R.id.save_person)

        button.setOnClickListener {


            val name = nameEditText.text.toString()
            val initialValue: Int = initialValueEditText.text.toString().toIntOrNull()!!

            val hashMap = HashMap<String, Any>()
            hashMap["name"] = name
            hashMap["initialValue"] = initialValue
            hashMap["value"] = initialValue


            val act: MainActivity = (activity as MainActivity?)!!
            act.newPerson(name, initialValue, initialValue)
            activity!!.supportFragmentManager.popBackStack()

        }


        return mView
    }
}