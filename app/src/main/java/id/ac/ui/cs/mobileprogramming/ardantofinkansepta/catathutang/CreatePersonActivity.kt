package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class CreatePersonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_person)

        var nameEditText = findViewById(R.id.name) as EditText

        var initialValueEditText = findViewById(R.id.initial_value) as EditText

        val button = findViewById<Button>(R.id.save_person)
        button.setOnClickListener {


            val name = nameEditText.text.toString()
            val initialValue: Int = initialValueEditText.text.toString().toIntOrNull()!!

            val hashMap = HashMap<String, Any>()
            hashMap["name"] = name
            hashMap["initialValue"] = initialValue
            hashMap["value"] = initialValue

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("data", hashMap)
            setResult(Activity.RESULT_OK, intent)

            finish()
        }


    }
}