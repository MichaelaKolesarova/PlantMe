package com.example.plantme

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.plantme.databinding.ActivityMainBinding
import java.util.*


/**
 * Activity Class with frame that helds all of the fragments and notification management
 */
class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var notificationAllowed = true

    /**
     * function sets fragment Home as the first fragment to be shown and initiates the
     * notification settings - time when notifie, intent on clicking onthe notification
     * and after a control if we use Android version above Oreo creates notification channel
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)




        notificationChannel()
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager

        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(System.currentTimeMillis())
        calendar.set(Calendar.HOUR_OF_DAY, 10)
        calendar.set(Calendar.MINUTE, 30)
        calendar.set(Calendar.SECOND, 0)
        if (Calendar.getInstance().after(calendar))
        {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        if (notificationAllowed)
        {
            val intent = Intent(this@MainActivity, NotificationBroadcast::class.java)
            val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY,
                pendingIntent)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        }

    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val switch: SwitchCompat =  menu.findItem(R.id.app_bar_switch).actionView as SwitchCompat;
        switch.setOnCheckedChangeListener { compoundButton, b ->
            notificationAllowed = b
            val editor = getSharedPreferences("com.example.srushtee.dummy", MODE_PRIVATE).edit()
            editor.putBoolean("service_status", switch.isChecked())
            editor.commit()
        }

        return true
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            //R.id. -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    /**
     * Handling restoring the whole state of the main activity so ň
     * fragments are  roconstructed well
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
    /**
     * Handling saving the whole state of the main activity so ň
     * fragments are  roconstructed well
     */
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }


    /**
     * Above version Oreo, chanel must be created specificly to communicate with the system
     */
    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Dnes nezabudnite na svoje kvietky"
            val description = "Pozrite sa, či je všetko v poriadku"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Notification", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }


}