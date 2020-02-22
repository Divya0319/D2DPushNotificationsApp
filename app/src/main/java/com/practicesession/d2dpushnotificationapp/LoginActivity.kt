package com.practicesession.d2dpushnotificationapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFireStore: FirebaseFirestore

    private fun sendToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        mFireStore = FirebaseFirestore.getInstance()

        val btLogin = findViewById<Button>(R.id.btLogin)

        btOpenRegister.setOnClickListener {
            val regIntent = Intent(this, RegisterActivity::class.java)
            startActivity(regIntent)

        }


        btLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val password = etPassLogin.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginProgressBar.visibility = View.VISIBLE

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {


                        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener{
                            instanceIdResult ->
                            val currentId = mAuth.currentUser?.uid

                            val tokenMap = hashMapOf<String, Any>()
                            tokenMap["token_id"] = instanceIdResult.result!!.token

                            mFireStore.collection("Users").document(currentId!!)
                                .update(tokenMap)
                                .addOnSuccessListener {
                                    sendToMain()
                                    loginProgressBar.visibility = View.INVISIBLE
                                }
                        }




                    } else {
                        loginProgressBar.visibility = View.INVISIBLE
                        Toast.makeText(this, "Error:" + task.exception?.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            sendToMain()
        }

    }
}
