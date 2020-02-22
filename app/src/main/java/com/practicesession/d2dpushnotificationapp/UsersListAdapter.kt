package com.practicesession.d2dpushnotificationapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UsersListAdapter(private val context: Context, private val mUsersList: MutableList<Users>) :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.users_list_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mUsersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user_name = mUsersList[position].name
        holder.userNameView.text = user_name
        Glide.with(context).load(mUsersList[position].image).into(holder.userImageView)

        val userId = mUsersList[position].userId

        holder.itemView.setOnClickListener {
            val sendIntent = Intent(context, SendActivity::class.java)
            sendIntent.putExtra("user_id", userId)
            sendIntent.putExtra("user_name", user_name)
            context.startActivity(sendIntent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImageView = itemView.findViewById<CircleImageView>(R.id.user_id_image)
        val userNameView = itemView.findViewById<TextView>(R.id.userName_single)

    }
}