package com.practicesession.d2dpushnotificationapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private var mImageUri: Uri? = null

    private lateinit var mStorageReference: StorageReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mStore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mStorageReference = FirebaseStorage.getInstance().reference.child("images")
        mAuth = FirebaseAuth.getInstance()
        mStore = FirebaseFirestore.getInstance()

        btOpenLogin.setOnClickListener {
            finish()
        }

        imgButtonUser.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        btRegister.setOnClickListener {
            if (mImageUri != null) {
                registerProgressBar.visibility = View.VISIBLE
                val name = etNameRegister.text.toString()
                val email = etEmailRegister.text.toString()
                val password = etPassRegister.text.toString()

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(
                        password
                    )
                ) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val userId = mAuth.currentUser?.uid
                                val userProfile = mStorageReference.child("$userId.jpg")

                                userProfile.putFile(mImageUri!!)
                                    .addOnSuccessListener { uploadTask ->
                                        val result = uploadTask.storage.downloadUrl
                                        var downloadUri: String
                                        result.addOnSuccessListener {
                                            downloadUri = it.toString()


                                            val token_id = FirebaseInstanceId.getInstance().token

                                                    val userMap = hashMapOf<String, Any>()
                                                    userMap["name"] = name
                                                    userMap["image"] = downloadUri
                                                    userMap["token_id"] = token_id!!
                                                    mStore.collection("Users").document(userId!!)
                                                        .set(userMap)
                                                        .addOnSuccessListener {

                                                            registerProgressBar.visibility =
                                                                View.INVISIBLE
                                                            sendToMain()
                                                        }


                                        }


                                    }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error:" + task.exception?.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        }
    }

    private fun sendToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun startLogin() {

    }

    companion object {
        const val PICK_IMAGE = 897
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            mImageUri = data?.data
            imgButtonUser.setImageURI(mImageUri)
        }
    }
}
