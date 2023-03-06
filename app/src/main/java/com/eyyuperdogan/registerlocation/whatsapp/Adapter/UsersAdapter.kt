package com.eyyuperdogan.registerlocation.whatsapp.Adapter
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.eyyuperdogan.registerlocation.whatsapp.ChatActivity
import com.eyyuperdogan.registerlocation.whatsapp.MainActivity
import com.eyyuperdogan.registerlocation.whatsapp.Models.User
import com.eyyuperdogan.registerlocation.whatsapp.R
import com.eyyuperdogan.registerlocation.whatsapp.databinding.RecyclerRowBinding
import com.squareup.picasso.Picasso

class UsersAdapter(var postsList:java.util.ArrayList<User.User>):RecyclerView.Adapter<UsersAdapter.PostHolder>(){
    class PostHolder(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

         holder.binding.userName.text=postsList.get(position).name +"  "+postsList.get(position).sorname
         holder.binding.Lastmassage.text=postsList.get(position).sorname

         Picasso.get().load(postsList.get(position).url).placeholder(R.drawable.avatar3).into(holder.binding.profileImage)
         holder.itemView.setOnClickListener(){
             val intent=Intent(holder.itemView.context,ChatActivity::class.java)
             intent.putExtra("uid",postsList.get(position).url)
             holder.itemView.context.startActivity(intent)
         }
    }

    override fun getItemCount(): Int {
        return postsList.size
    }
}