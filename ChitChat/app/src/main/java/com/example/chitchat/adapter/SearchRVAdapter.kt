package com.example.chitchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chitchat.R
import com.example.chitchat.databinding.CardUsersBinding
import com.example.chitchat.model.User
import com.example.chitchat.ui.message_acts.ChatMessageActivity
import frame_transition.Transition

import print.Print

class SearchRVAdapter(var products:ArrayList<User>):RecyclerView.Adapter<SearchRVAdapter.MyViewHolder>() {
    var p:Print?=null
    var ctx:Context?=null
    class MyViewHolder(val binding:CardUsersBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding:CardUsersBinding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.card_users,
                parent, false
        )
        p = Print(parent.context)
        ctx=parent.context

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.pd= products.get(position)
        Glide.with(ctx!!)
            .load(R.drawable.megha_akash)
            .centerCrop()
            .into(holder.binding.profileImage)

        holder.binding.userCard.setOnClickListener {
            val intent= Intent(ctx,ChatMessageActivity::class.java)
            intent.putExtra("UserObj",products[position])
            ctx!!.startActivity(intent)
        }
    }
}