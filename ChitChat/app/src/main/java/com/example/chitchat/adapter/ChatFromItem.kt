package com.example.chitchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.databinding.CardUsersBinding
import com.example.chitchat.databinding.ChatFromRowBinding
import com.example.chitchat.databinding.ChatToRowBinding
import com.example.chitchat.model.User
import com.example.chitchat.ui.message_acts.ChatMessageActivity
import com.example.chitchat.ui.message_acts.Message
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.chat_to_row.view.*
import print.Print

class ChatFromItem(val products:ArrayList<Message>): RecyclerView.Adapter<ChatFromItem.MyViewHolder>() {
    var p: Print?=null
    var ctx: Context?=null
    class MyViewHolder(val binding:Any,itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): MyViewHolder {
        if (pos==1) {
            val binding: ChatFromRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.chat_from_row,
                parent, false
            )
            p = Print(parent.context)
            ctx = parent.context

            //p?.sprintf("FromUserOnCreate")
            println("FromUserOnCreate")
            return MyViewHolder(binding, binding.root)
        }
        else {
            val binding: ChatToRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.chat_to_row,
                parent, false
            )
            p = Print(parent.context)
            ctx = parent.context

            //p?.sprintf("ToUserOnCreate")
            println("ToUserOnCreate")
            return MyViewHolder(binding, binding.root)
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val msg=products[position]
        val uid=FirebaseAuth.getInstance().uid
        if(msg.fromId==uid){
            val binding=holder.binding as ChatFromRowBinding
            binding.txt.text=msg.text
        }
        else{
            val binding=holder.binding as ChatToRowBinding
            binding.txt.text=msg.text
        }


    }

    override fun getItemViewType(position: Int): Int {
        val msg=products[position]
        val uid=FirebaseAuth.getInstance().uid
        return if(msg.fromId==uid){
            1
        }
        else {
            0
        }
    }
}