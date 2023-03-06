package com.eyyuperdogan.registerlocation.whatsapp
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.eyyuperdogan.registerlocation.whatsapp.Models.User
import com.eyyuperdogan.registerlocation.whatsapp.databinding.ActivitySignUpActivtyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivitySignUpActivtyBinding
private lateinit var auth:FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore:FirebaseFirestore
private lateinit var dialog: AlertDialog
class SignUpActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)



        auth= Firebase.auth //auth=FirebaseAuth.getInstance() şeklindede kullanılabilir
        firestore= FirebaseFirestore.getInstance()

        var builder= AlertDialog.Builder(this)

        //ekranda bilgilendirme mesjı gösterir
        builder.setTitle("Lütfen bekleyiniz...")
        builder.setMessage("Hesabınız oluşturuluyor lütfen bekleyiniz")
        builder.setCancelable(false)
        dialog=builder.create()


        //veri tabanına username ve password kayıt eder
        binding.btnSignUp.setOnClickListener(){
            val userName= binding.textUserName.text.toString()
            val email= binding.textEmail.text.toString()
            val password= binding.textPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && userName.isNotEmpty()) {
                dialog.show()
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {


                    val userHasMap = hashMapOf<String, Any>()
                    val id = it.user?.uid.toString()
                    userHasMap.put("userName", userName)
                    userHasMap.put("id", id)
                    userHasMap.put("password", password)
                    userHasMap.put("email", email)
                    val user = User(email, userName, password)
                    firestore.collection("UsersNameRegister").add(userHasMap).addOnSuccessListener {
                    //success (başarılı)
                        auth.signOut()
                        intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "hesabınız oluşturuldu", Toast.LENGTH_LONG).show()
                     dialog.dismiss()
                    }.addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()

                    }

                }
            }
            else{
                Toast.makeText(this,"eposta ve şifre boş olamaz!",Toast.LENGTH_LONG).show()
            }
        }






        //activity değişikliği
        binding.txtSign.setOnClickListener(){
            intent= Intent(this,Login::class.java)
            startActivity(intent)
        }






    }
}