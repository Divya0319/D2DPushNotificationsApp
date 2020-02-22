package com.practicesession.d2dpushnotificationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class NotificationActivity : AppCompatActivity() {
    private lateinit var mNotifData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        mNotifData = findViewById(R.id.notif_data)

        val dataFrom  =intent.getStringExtra("from_name")
        val dataMessage = intent.getStringExtra("message")

        mNotifData.text = "FROM: $dataFrom  \nMessage: $dataMessage"
    }
}
