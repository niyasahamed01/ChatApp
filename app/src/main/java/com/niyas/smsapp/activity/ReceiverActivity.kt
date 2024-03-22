package com.niyas.smsapp.activity


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.niyas.smsapp.R
import com.niyas.smsapp.adapter.MessageAdapter
import com.niyas.smsapp.util.ChatMessage

class ReceiverActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private val chatMessages = ArrayList<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.messagesRecyclerView)
        messageAdapter = MessageAdapter(chatMessages)
        recyclerView.adapter = messageAdapter

        loadMessages()
    }

    @SuppressLint("NotifyDataSetChanged")@Suppress("DEPRECATION")
    private fun loadMessages() {
        val receivedMessages = intent.getParcelableArrayListExtra<ChatMessage>("chatMessages")
        receivedMessages?.let {
            chatMessages.addAll(it)
            messageAdapter.notifyDataSetChanged()
        }
    }

}