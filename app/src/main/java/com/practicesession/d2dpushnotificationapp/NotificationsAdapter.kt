package com.practicesession.d2dpushnotificationapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class NotificationsAdapter(
    private val context: Context,
    private val mNotificationList: MutableList<Notifications>
) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    private var mFireStore = FirebaseFirestore.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mNotificationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fromId = mNotificationList[position].from
        holder.notifContent.text = mNotificationList[position].message

        mFireStore.collection("Users").document(fromId).get()
            .addOnSuccessListener { documentSnapshot ->

                val name = documentSnapshot.getString("name")
                val image = documentSnapshot.getString("image")

                holder.notifName.text = name


                Glide.with(context).load(image).placeholder(R.mipmap.ic_group)
                    .into(holder.userImage)

            }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage = itemView.findViewById<CircleImageView>(R.id.notif_user_image)
        val notifName = itemView.findViewById<TextView>(R.id.name_notification)
        val notifContent = itemView.findViewById<TextView>(R.id.tv_notification_content)

    }
}