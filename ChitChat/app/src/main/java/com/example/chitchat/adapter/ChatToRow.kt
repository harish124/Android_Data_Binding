package com.example.chitchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chitchat.R
import com.example.chitchat.databinding.ChatFromRowBinding
import com.example.chitchat.databinding.ChatToRowBinding
import com.example.chitchat.ui.message_acts.Message
import print.Print

class ChatToRow(val products:ArrayList<Message>): RecyclerView.Adapter<ChatToRow.MyViewHolder>() {
    var p: Print?=null
    var ctx: Context?=null
    class MyViewHolder(val binding: ChatToRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ChatToRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.chat_to_row,
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
        //holder.binding.fromUserText.text = products[position].text
    }

}