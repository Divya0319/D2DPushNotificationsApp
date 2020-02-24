package com.practicesession.d2dpushnotificationapp


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {
    private lateinit var mUserListView: RecyclerView
    private var mUsersList = mutableListOf<Users>()
    private lateinit var mUsersListAdapter: UsersListAdapter
    private lateinit var mFireStore: FirebaseFirestore
    private lateinit var registration: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userFragmentView = inflater.inflate(R.layout.fragment_users, container, false)

        mFireStore = FirebaseFirestore.getInstance()

        mUserListView = userFragmentView.findViewById(R.id.user_list_rec_view)
        mUsersListAdapter = UsersListAdapter(container?.context!!, mUsersList)

        mUserListView.setHasFixedSize(true)
        mUserListView.layoutManager = LinearLayoutManager(container.context)
        mUserListView.adapter = mUsersListAdapter

        return userFragmentView
    }

    override fun onStart() {
        super.onStart()

        mUsersList.clear()

        registration = mFireStore.collection("Users").addSnapshotListener { documentSnapShots, e ->
            if (e != null) {
                Log.d("FirebaseError", e.message!!)
            } else {
                for (doc in documentSnapShots!!.documentChanges) {
                    if (doc.type == DocumentChange.Type.ADDED) {

                        val userId = doc.document.id
                        val users: Users = doc.document.toObject(Users::class.java).withId(userId)
                        mUsersList.add(users)

                        mUsersListAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        registration.remove()
    }
}
