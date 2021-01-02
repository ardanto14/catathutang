package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.PersonRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.repository.TransactionRepository
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.service.BackupService
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.service.NotificationService


class BackupFragment : Fragment() {

    private var isConnected = false;
    private var isWifi = false

    private val database by lazy { MainRoomDatabase.getDatabase(activity!!) }
    private val personRepository by lazy { PersonRepository(database.personDao()) }
    private val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val mView: View = inflater.inflate(R.layout.backup, container, false)

        val button = mView.findViewById<Button>(R.id.check_connection)

        button.setOnClickListener {
            checkConnection()

            val textView = mView.findViewById(R.id.is_connected) as TextView
            textView.text = if (isConnected) "Connected" else "Not Connected"

            val textView2 = mView.findViewById(R.id.is_wifi) as TextView
            textView2.text = if (isConnected) (if (isWifi) "Wifi" else "Mobile Data") else "Not Connected"
        }

        val button2 = mView.findViewById<Button>(R.id.backup)

        button2.setOnClickListener {
            checkConnection()

            if (isConnected && isWifi) {
                backup()
            } else {
                Toast.makeText(activity!!, "You should connect to Wi-Fi to do Backup", Toast.LENGTH_SHORT).show()
            }
        }

        return mView
    }

    private fun backup() {

        activity!!.startService(Intent(activity!!.applicationContext, BackupService::class.java))

        activity!!.supportFragmentManager.popBackStack()
    }



    private fun checkConnection() {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnected == true
        isWifi = !cm.isActiveNetworkMetered
    }
}