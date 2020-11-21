package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R


class BatteryLowBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        
        StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            Toast.makeText(
                    context,
                    R.string.battery_low_notification,
                    Toast.LENGTH_LONG
            ).show()
        }
    }
}