package com.niyas.smsapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.niyas.smsapp.R
import com.niyas.smsapp.adapter.ChatAdapter
import com.niyas.smsapp.util.AESUtils
import com.niyas.smsapp.util.ChatMessage


class SenderActivity : AppCompatActivity() {

    private lateinit var messageList: MutableList<ChatMessage>
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: ChatAdapter
    private lateinit var messageEditText: EditText

    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        messageEditText = findViewById(R.id.messageEditText)
        val sendButton: Button = findViewById(R.id.sendButton)

        // Initialize message list and adapter
        messageList = mutableListOf()
        messageAdapter = ChatAdapter(messageList)
        recyclerView.adapter = messageAdapter

        // Handle sending message
        sendButton.setOnClickListener {
            loadMessage()
        }
    }

    private fun loadMessage() {
        val messageText = messageEditText.text.toString()
        if (messageText.isNotEmpty()) {
            val key = "your_secret_key"
            val encryptedMessage = AESUtils.encrypt(messageText, key)
            val decryptedMessage = AESUtils.decrypt(encryptedMessage, key)
            val chatMessage = ChatMessage(decryptedMessage, isSent = true)
            // Add the new message to the list
            messageList.add(chatMessage)
            messageAdapter.notifyItemInserted(messageList.size - 1)
            recyclerView.scrollToPosition(messageList.size - 1)

            // Clear the message input field
            messageEditText.setText("")

            val intent = Intent(this, ReceiverActivity::class.java).apply {
                putParcelableArrayListExtra("chatMessages", ArrayList(messageList))
            }
            startActivity(intent)
        }
    }


}

