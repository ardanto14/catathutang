package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.R
import java.util.*

class NotificationService : Service() {

    private var builder = NotificationCompat.Builder(this, "pengingat")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Catat Hutang")
            .setContentText("jangan lupa tagih hutangmu hehe")
            .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("jangan lupa tagih hutangmu hehe"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    @RequiresApi(Build.VERSION_CODES.O) override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("pengingat", "pengingat", importance).apply {
                description = "pengingat tagih"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        class MyTimerTask : TimerTask() {
            override fun run() {
                with(NotificationManagerCompat.from(applicationContext)) {
                    // notificationId is a unique int for each notification that you must define
                    notify(1, builder.build())
                }
            }
        }

        val myTask = MyTimerTask()
        val myTimer = Timer()

        myTimer.schedule(myTask, 5000, 3600000)

        return START_NOT_STICKY
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}