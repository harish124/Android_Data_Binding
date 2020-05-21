package com.example.chitchat.ui.message_acts

import android.app.Activity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chitchat.R
import com.example.chitchat.adapter.ChatFromItem
import com.example.chitchat.adapter.ChatToRow
import com.example.chitchat.databinding.ActivityChatMessageBinding
import com.example.chitchat.model.User
import com.example.chitchat.ui.FirstScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import frame_transition.Transition

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_chat_message.*

class ChatMessageActivity : Activity() {

    private var binding:ActivityChatMessageBinding?=null
    private val mAuth = FirebaseAuth.getInstance()
    private val database= FirebaseDatabase.getInstance()
    private val transition=Transition(this)
    private val messages= arrayListOf<Message>()
    private val chatFromAdapter= ChatFromItem(messages)
    private val chatToAdapter= ChatToRow(messages)
    var user:User?=null


    private fun configRecyclerView() {
        val lm=LinearLayoutManager(this@ChatMessageActivity)
        lm.stackFromEnd=true
        binding?.recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager=lm
            adapter=ScaleInAnimationAdapter(chatFromAdapter).apply{
                setFirstOnly(false)
                setDuration(1000)
                setHasStableIds(false)
                setInterpolator(OvershootInterpolator(.100f))
            }
            itemAnimator= SlideInUpAnimator(OvershootInterpolator(1f))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_chat_message)

        user=fetchUser()
        binding!!.toolbarChat.title=user?.uname
        //setSupportActionBar(toolbar_chat)


        configRecyclerView()

        binding!!.sendBtn.setOnClickListener{
            sendToFirebase()
            binding!!.txtMsg.setText("")
            binding!!.recyclerView.scrollToPosition(messages.size)
        }

        binding!!.backBtn.setOnClickListener{
            transition.goTo(FirstScreen::class.java)
            finish()
        }

        listenForMessages()

    }

    private fun listenForMessages() {
        database.getReference("messages")
            .addValueEventListener(
                object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var i=0
                        messages.clear()
                        chatFromAdapter.notifyDataSetChanged()
                        p0.children.forEach{
                            if(it.exists()){
                                val msg=it.getValue(Message::class.java) ?: Message(text="Error")
                                println("Message: $msg")
                                messages.add(msg)
                                chatFromAdapter.notifyItemInserted(i)
                                i+=1
                            }
                        }
                    }

                })


    }

    private fun constructMessage(key:String=""):Message{
        return Message(
            key,
            mAuth.uid!!,
            user!!.uid,
            binding!!.txtMsg.text.toString(),
            System.currentTimeMillis()/1000
        )
    }

    private fun sendToFirebase() {
        val ref=database.getReference("messages").push()

        val msg=constructMessage(ref.key.toString())
        if(msg.text.isEmpty()){
            return
        }
        ref.setValue(msg)
        .addOnSuccessListener {
            println("Sent to fb")
        }
    }

    private fun fetchUser() = intent.getParcelableExtra<User>("UserObj")



}

data class Message(
    val msgId:String="",
    val fromId:String="",
    val toId:String="",
    val text:String="",
    val time:Long=12345678
)

