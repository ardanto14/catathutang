package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.service

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.RequestQueueSingleton
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction
import org.json.JSONArray
import org.json.JSONObject
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase


class BackupService : Service() {


    @RequiresApi(Build.VERSION_CODES.O) override fun onCreate() {
        super.onCreate()



    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val database = MainRoomDatabase.getDatabase(applicationContext)

        val allTransaction: List<Transaction> = database.transactionDao().getAllTransactionList()
        val allPerson: List<Person> = database.personDao().getAllPersonList()


        val url = "https://6e24438104a9f8275e8f984bb16b0ff8.m.pipedream.net"


        val jsonBody = JSONObject()

        val person = JSONArray()
        val trxs = JSONArray()
        allPerson!!.forEach {
            val prsn = JSONObject()
            prsn.put("id", it.id)
            prsn.put("name", it.name)
            prsn.put("value", it.value)
            prsn.put("initialValue", it.initialValue)
            person.put(prsn)
        }

        allTransaction!!.forEach {
            val trx = JSONObject()
            trx.put("id", it.id)
            trx.put("value", it.value)
            trx.put("note", it.note)
            trx.put("userId", it.userId)
            trxs.put(trx)
        }

        jsonBody.put("persons", person)
        jsonBody.put("transactions", trxs)


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            Response.Listener { response ->


                Toast.makeText(applicationContext, "Successfully backup", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                error.printStackTrace()
            }
        )


        RequestQueueSingleton.getInstance(applicationContext).addToRequestQueue(jsonObjectRequest)

        return START_NOT_STICKY
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}