package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.MainActivity
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R

class CreateTransactionFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ) : View {

        val mView: View = inflater.inflate(R.layout.create_transaction, container, false)

        var valueEditText = mView.findViewById(R.id.value) as EditText

        var noteEditText = mView.findViewById(R.id.note) as EditText

        val button = mView.findViewById<Button>(R.id.save_transaction)


        val userId = arguments?.getInt("userId", -1)

        if (userId == -1 || userId == null) {
            Toast.makeText(
                    activity,
                    R.string.add_transaction_failed,
                    Toast.LENGTH_LONG
            ).show()

            activity!!.supportFragmentManager.popBackStack()


        }

        button.setOnClickListener {
            val note = noteEditText.text.toString()
            val value: Int = valueEditText.text.toString().toIntOrNull()!!


            val act: MainActivity = (activity as MainActivity?)!!
            act.newTransaction(note, value, userId!!)
            activity!!.supportFragmentManager.popBackStack()


        }

        return mView
    }
}