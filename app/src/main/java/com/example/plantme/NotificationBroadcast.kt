package com.example.plantme


import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * Communication Between system and notification reaction
 */
class NotificationBroadcast(): BroadcastReceiver() {
    /**
     * creates form of the notification
     * sets Main Activity as the activity to be opened after clicking on the notification
     */
    override fun onReceive(context: Context, intent: Intent?) {
        val repeating_Intent = Intent(context, MainActivity::class.java)
        repeating_Intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            repeating_Intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(
                    Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.icon
                        ), 128, 128, false
                    )
                )
                .setContentTitle("Dnes nezabudnite na svoje kvietky")
                .setContentText("Pozrite sa, či je všetko v poriadku")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(200, builder.build())
    }
}