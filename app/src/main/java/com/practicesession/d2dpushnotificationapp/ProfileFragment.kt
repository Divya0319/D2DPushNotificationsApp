package com.practicesession.d2dpushnotificationapp


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mLogoutButton: Button
    private lateinit var mProfileImage: CircleImageView
    private lateinit var mProfileName: TextView
    private lateinit var mFireStore: FirebaseFirestore
    private var mUserId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileFragmentView = inflater.inflate(R.layout.fragment_profile, container, false)

        mAuth = FirebaseAuth.getInstance()
        mFireStore = FirebaseFirestore.getInstance()

        mUserId = mAuth.currentUser!!.uid

        mLogoutButton = profileFragmentView.findViewById(R.id.logout_button)
        mProfileImage = profileFragmentView.findViewById(R.id.img_profile)
        mProfileName = profileFragmentView.findViewById(R.id.profile_name)

        mFireStore.collection("Users").document(mUserId).get().addOnSuccessListener {
            documentSnapshot ->
            val userName = documentSnapshot.getString("name")
            val userImage = documentSnapshot.getString("image")

            mProfileName.text = userName

            Glide.with(container!!.context).load(userImage).placeholder(R.mipmap.ic_group).into(mProfileImage)
        }


        mLogoutButton.setOnClickListener {

            val tokenMapRemove = hashMapOf<String, Any>()
            tokenMapRemove["token_id"] = FieldValue.delete()

            mFireStore.collection("Users").document(mUserId).update(tokenMapRemove)
                .addOnSuccessListener {
                    mAuth.signOut()
                    val loginIntent = Intent(container?.context, LoginActivity::class.java)
                    startActivity(loginIntent)
                    activity?.finish()
                }



        }
        return profileFragmentView
    }


}
