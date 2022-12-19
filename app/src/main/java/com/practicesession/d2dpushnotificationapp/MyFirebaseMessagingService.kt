package com.practicesession.d2dpushnotificationapp

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val messageTitle = remoteMessage.notification?.title
        val messageBody = remoteMessage.notification?.body
        val clickAction = remoteMessage.notification?.clickAction
        val dataMessage = remoteMessage.data["message"]
        val dataFrom = remoteMessage.data["from_name"]

        val notificationHelper = NotificationHelper(this)
        notificationHelper.buildNotification(
            messageTitle!!, messageBody!!,
            "Firebase Channel", "Firebase Push notification channel",
            "1",
            clickAction!!,
            dataMessage!!,
            dataFrom!!

        )
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("--------", "--------------------")
        Log.d("NewToken", p0)
        Log.d("--------", "--------------------")
    }

}