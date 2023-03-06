package com.eyyuperdogan.registerlocation.whatsapp

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eyyuperdogan.registerlocation.whatsapp.databinding.ActivityProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import io.grpc.Context.Storage
import kotlinx.android.synthetic.main.activity_profile.view.*
import java.lang.ref.Reference
import java.util.*

private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
private lateinit var permissionLauncher: ActivityResultLauncher<String>
private lateinit var binding:ActivityProfileBinding
private lateinit var dialog: AlertDialog
private lateinit var storage: FirebaseStorage
private lateinit var firestore: FirebaseFirestore

private lateinit var auth:FirebaseAuth
var selectedPicture : Uri? = null
class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var builder=AlertDialog.Builder(this)
        auth=Firebase.auth
        firestore=Firebase.firestore//database
        storage=Firebase.storage//görsel

        builder.setTitle("Lütfen bekleyiniz...")
        builder.setMessage("Hesabınıza giriş yapılıyor")
        builder.setCancelable(false)

        dialog=builder.create()
        registerLauncher()
        //rasgele urı ıd oluşrulur
        var uuıd= UUID.randomUUID()
        var imageName="$uuıd.jpg"


        binding.imageAdd.setOnClickListener(){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Snackbar.make(it, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("izin ver",
                        View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }
        var number=intent.getStringExtra("numberPhone")
        println(number.toString())
        binding.buttonContine.setOnClickListener(){
                 val name= binding.textName.text.toString()
                 val sorname= binding.textSorname.text.toString()
                var referance= storage.reference
                var imageReference=referance.child("images").child(imageName)
                if (selectedPicture!=null){
                    imageReference.putFile(selectedPicture!!).addOnSuccessListener {

                        //downloadurl url->firestore(url yi alıyoruz)
                        var pictureReferance = referance.child("images").child(imageName)
                        pictureReferance.downloadUrl.addOnSuccessListener {
                            val downloadurl = it.toString()

                            //veri alıyoruz  veri tabanında gösteriyoruz(downloadurl,eposta,tarih,yorum)
                            if (auth.currentUser != null) {
                                dialog.show()

                                var postHasMap = hashMapOf<String, Any>()
                                postHasMap.put("downloadurl", downloadurl)
                                postHasMap.put("giriş tarihi", Timestamp.now())
                                postHasMap.put("id", auth.uid.toString())
                                postHasMap.put("adı", name)
                                postHasMap.put("telefon numarası", auth.currentUser!!.phoneNumber.toString())
                                postHasMap.put("soyadı",sorname)


                                //veri tabanımıza kaydediyoruz (downloadurl,eposta,tarih,yorum)
                                firestore.collection("NumberPhoneRegister").add(postHasMap).addOnSuccessListener {

                                    Toast.makeText(this,"profile oluşturuldu", Toast.LENGTH_LONG).show()
                                    intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                }.addOnFailureListener {
                                    dialog.dismiss()
                                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                                }
                            }
                        }.addOnFailureListener {
                            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }




    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        selectedPicture = intentFromResult.data

                        selectedPicture.let {
                            binding.imageAdd.setImageURI(it)
                        }
                    }
                }
            }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intenToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intenToGallery)
                } else {
                    Toast.makeText(this, "izin verilmedi!", Toast.LENGTH_LONG).show()
                }
            }
    }
}