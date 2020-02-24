package com.practicesession.d2dpushnotificationapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

/**
 * A simple [Fragment] subclass.
 */
class NotificationsFragment : Fragment() {
    private lateinit var registration: ListenerRegistration
    private lateinit var mNotificationsList: RecyclerView
    private lateinit var mNotifList: MutableList<Notifications>
    private lateinit var mNotificationsAdapter: NotificationsAdapter
    private lateinit var mFireStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val notifFragmentView = inflater.inflate(R.layout.fragment_notification, container, false)

        mNotifList = mutableListOf()

        mNotificationsAdapter = NotificationsAdapter(container!!.context, mNotifList)

        mNotificationsList = notifFragmentView.findViewById(R.id.notifications_list)
        mNotificationsList.setHasFixedSize(true)
        mNotificationsList.layoutManager = LinearLayoutManager(container.context)
        mNotificationsList.adapter = mNotificationsAdapter

        mFireStore = FirebaseFirestore.getInstance()

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        registration =
            mFireStore.collection("Users").document(currentUserId!!).collection("Notifications")
                .addSnapshotListener { querySnapshot, _ ->
                    for (doc in querySnapshot!!.documentChanges) {
                        val notifications = doc.document.toObject(Notifications::class.java)
                        mNotifList.add(notifications)

                        mNotificationsAdapter.notifyDataSetChanged()
                    }
                }
        return notifFragmentView
    }

    override fun onPause() {
        super.onPause()

        registration.remove()
    }

}
