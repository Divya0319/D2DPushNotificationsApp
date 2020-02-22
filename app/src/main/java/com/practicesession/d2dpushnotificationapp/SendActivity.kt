package com.practicesession.d2dpushnotificationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SendActivity : AppCompatActivity() {

    private lateinit var mUserIdView: TextView

    private var mUserId = ""
    private var mUserName = ""
    private var mCurrentId = ""

    private lateinit var mSendButton: Button
    private lateinit var mMessageView: EditText
    private lateinit var mMessageProgress: ProgressBar

    private lateinit var mFireStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        mUserIdView = findViewById(R.id.user_id_view)
        mSendButton = findViewById(R.id.sendButton)
        mMessageView = findViewById(R.id.message_view_et)
        mMessageProgress = findViewById(R.id.notification_progress)

        mFireStore = FirebaseFirestore.getInstance()
        mCurrentId = FirebaseAuth.getInstance().uid!!
        mUserName = intent.getStringExtra("user_name")!!

        mUserId = intent.getStringExtra("user_id")!!
        mUserIdView.text = mUserId
        mUserIdView.text = "Send to: $mUserName"

        mSendButton.setOnClickListener {
            val message = mMessageView.text.toString()

            if (!TextUtils.isEmpty(message)) {
                mMessageProgress.visibility = View.VISIBLE

                val notificationMessage = hashMapOf<String, Any>()
                notificationMessage["message"] = message
                notificationMessage["from"] = mCurrentId

                mFireStore.collection("Users/$mUserId/Notifications").add(notificationMessage)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Notification Sent!!", Toast.LENGTH_LONG).show()
                        mMessageView.setText("")
                        mMessageProgress.visibility = View.INVISIBLE
                    }
                    .addOnFailureListener { ex ->
                        Toast.makeText(this, "Error:${ex.message}", Toast.LENGTH_LONG).show()
                        mMessageProgress.visibility = View.INVISIBLE
                    }

            }
        }
    }
}
