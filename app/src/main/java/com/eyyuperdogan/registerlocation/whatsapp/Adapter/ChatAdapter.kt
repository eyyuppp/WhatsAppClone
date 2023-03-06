package com.eyyuperdogan.registerlocation.whatsapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eyyuperdogan.registerlocation.whatsapp.Models.User
import com.eyyuperdogan.registerlocation.whatsapp.databinding.RecyclerRowBinding

class ChatAdapter(var arrayList: ArrayList<User.User>):RecyclerView.Adapter<UsersAdapter.PostHolder>(){
    class PostHolder(val binding: RecyclerRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.PostHolder {
        val binding= RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UsersAdapter.PostHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersAdapter.PostHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}