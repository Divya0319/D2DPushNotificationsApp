package com.practicesession.d2dpushnotificationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_SOUND
import androidx.core.app.NotificationManagerCompat


class NotificationHelper(private val context: Context) {
    private var notificationManagerCompat = NotificationManagerCompat.from(context)


    fun buildNotification(
        textTitle: String,
        textContent: String,
        channelName: String,
        channelDesc: String,
        channelId: String,
        clickAction: String,
        dataMessage: String,
        dataFrom: String
    ) {
        createNotificationChannel(channelName, channelDesc, channelId)

        val intent = Intent(context, NotificationActivity::class.java).apply {
            action = clickAction
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("message", dataMessage)
            putExtra("from_name", dataFrom)
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_group)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(500, 1000))
            .setDefaults(DEFAULT_SOUND)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(textContent)
            )
            .setAutoCancel(true)


        showNotification(builder)

    }

    private fun showNotification(builder: NotificationCompat.Builder) {
        val dataProcessor = DataProcessor(context)

        notificationManagerCompat.notify(DataProcessor.getCurrentNotificationId(), builder.build())

    }

    private fun createNotificationChannel(
        channelName: String,
        channelDesc: String,
        channelId: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDesc
                enableVibration(true)
                setShowBadge(true)
                vibrationPattern = longArrayOf(500, 1000)
            }

            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "1"
    }
}