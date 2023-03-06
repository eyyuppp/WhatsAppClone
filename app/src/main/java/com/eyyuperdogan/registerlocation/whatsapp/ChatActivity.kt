package com.eyyuperdogan.registerlocation.whatsapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eyyuperdogan.registerlocation.whatsapp.Models.MessageModel
import com.eyyuperdogan.registerlocation.whatsapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

private lateinit var binding:ActivityChatBinding
private lateinit var db:FirebaseFirestore
private lateinit var senderUid:String //gönderici kimliği
private lateinit var receiverUid:String //alıcı kimliği
private lateinit var senderRoom:String //gönderici odası
private lateinit var receiverRoom:String //alıcı odası

class ChatActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= FirebaseFirestore.getInstance()


        senderUid=FirebaseAuth.getInstance().uid.toString()
        receiverUid=intent.getStringExtra("uid")!!

        senderRoom= senderUid+ receiverUid
        receiverRoom= receiverUid+ senderUid



        binding.btnSharing.setOnClickListener(){
            val chatMessage= binding.editTextChat.text.toString()
            if (chatMessage.isNotEmpty()) {
                val message=MessageModel(binding.editTextChat.text.toString(), senderUid,Date().time)

                db.collection("Chats").add(message).addOnSuccessListener {

                    Toast.makeText(this, "mesaj gönderildi", Toast.LENGTH_LONG).show()
                    binding.editTextChat.text.clear()
                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    binding.editTextChat.text.clear()

                }

            }
            else{
                Toast.makeText(this, "mesaj yazın", Toast.LENGTH_LONG).show()
            }
        }
    }
}