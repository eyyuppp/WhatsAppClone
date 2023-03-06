package com.eyyuperdogan.registerlocation.whatsapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eyyuperdogan.registerlocation.whatsapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

private lateinit var auth:FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore: FirebaseFirestore
@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityLoginBinding
private lateinit var dialog: AlertDialog
class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var builder=AlertDialog.Builder(this)


        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        builder.setTitle("Lütfen bekleyiniz...")
        builder.setMessage("Hesabınıza giriş yapılıyor")
        builder.setCancelable(false)

        dialog=builder.create()
        if (auth.currentUser!=null){
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



      txtSignLogin.setOnClickListener(){
          intent = Intent(this@Login, SignUpActivity::class.java)
          startActivity(intent)
      }

        binding.txtSignUpPhone.setOnClickListener(){
            intent = Intent(this@Login, NumberActivty::class.java)
            startActivity(intent)
        }







        binding.btnSignIn.setOnClickListener() {

            val email = binding.textEmail.text.toString()
            val password = binding.textPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                dialog.show()
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@Login, "hesabınıza giriş yapıldı (:", Toast.LENGTH_LONG).show()

                }.addOnFailureListener {
                    Toast.makeText(this@Login, it.localizedMessage, Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(this@Login, "eposta ve şifre boş olamaz!", Toast.LENGTH_LONG).show()

            }
        }





    }
}