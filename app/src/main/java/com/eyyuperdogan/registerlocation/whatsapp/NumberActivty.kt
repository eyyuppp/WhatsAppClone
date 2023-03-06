package com.eyyuperdogan.registerlocation.whatsapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.wifi.hotspot2.pps.Credential
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eyyuperdogan.registerlocation.whatsapp.databinding.ActivityNumberActivtyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore: FirebaseFirestore
@SuppressLint("StaticFieldLeak")
private lateinit var binding:ActivityNumberActivtyBinding
class NumberActivty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNumberActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth= FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()


        binding.buttonContine.setOnClickListener(){
            val numberPhone= binding.textNumberPhone.text.toString()

         if (numberPhone.isNotEmpty()){
             intent = Intent(this@NumberActivty, VerfyNumberActivty::class.java)
             intent.putExtra("numberPhone", binding.textNumberPhone.text.toString())
             startActivity(intent)


         }
            else
         {
             Toast.makeText(this@NumberActivty, "Telefon numarası boş olamaz!", Toast.LENGTH_LONG).show()
         }
        }
    }


}