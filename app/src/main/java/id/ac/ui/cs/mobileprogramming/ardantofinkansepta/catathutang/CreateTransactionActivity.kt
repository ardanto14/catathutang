package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CreateTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_transaction)

        var valueEditText = findViewById(R.id.value) as EditText

        var noteEditText = findViewById(R.id.note) as EditText

        val button = findViewById<Button>(R.id.save_transaction)

        val userId = intent.getIntExtra("userId", -1)

        if (userId == -1) {
            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_CANCELED, intent)

            finish()
        }


        button.setOnClickListener {
            val note = noteEditText.text.toString()
            val value: Int = valueEditText.text.toString().toIntOrNull()!!

            val hashMap = HashMap<String, Any>()
            hashMap["userId"] = userId
            hashMap["note"] = note
            hashMap["value"] = value

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("data", hashMap)
            setResult(Activity.RESULT_OK, intent)

            finish()

        }
    }
}