package com.eyyuperdogan.registerlocation.whatsapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eyyuperdogan.registerlocation.whatsapp.databinding.ActivityVerfyNumberActivtyBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit
import javax.security.auth.callback.Callback

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityVerfyNumberActivtyBinding
private lateinit var callbacks:Callback
private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore: FirebaseFirestore
@SuppressLint("StaticFieldLeak")
private lateinit var dialog: AlertDialog
var verificationId:String?=null
class VerfyNumberActivty : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityVerfyNumberActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth= FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()

        val builder=AlertDialog.Builder(this)
        builder.setTitle("Lütfen bekleyiniz...")
        builder.setMessage("İşleminiz gerçekleştiriliyor")

        dialog=builder.create()

        verfyNum()




        binding.buttonSignNumber.setOnClickListener(){
            val txtSms= binding.TextSms.text.toString()
            if (txtSms.isNotEmpty()){
                dialog.show()
            val credential=PhoneAuthProvider.getCredential(verificationId!!,txtSms)

                auth.signInWithCredential(credential).addOnSuccessListener {

                    intent = Intent(this@VerfyNumberActivty, Profile::class.java)
                    startActivity(intent)

                }.addOnFailureListener {
                    Toast.makeText(this@VerfyNumberActivty, it.localizedMessage, Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            }
            else
            {
                Toast.makeText(this@VerfyNumberActivty," şifre boş geçilemez", Toast.LENGTH_LONG).show()

            }
        }
    }



    @SuppressLint("SuspiciousIndentation")
    fun verfyNum(){

            val numberPhone="+90"+intent.getStringExtra("numberPhone")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(numberPhone.toString())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this@VerfyNumberActivty)        // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    //doğrulama tamamlandı
                    dialog.show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    //doğrulama tamamlanamadı
                    dialog.dismiss()
                    Toast.makeText(this@VerfyNumberActivty, "Lütfen tekrar deneyiniz!", Toast.LENGTH_LONG).show()

                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    verificationId=p0
                }

            }).build()

            PhoneAuthProvider.verifyPhoneNumber(options)


    }
}
