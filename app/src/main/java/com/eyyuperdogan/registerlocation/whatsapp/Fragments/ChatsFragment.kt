package com.eyyuperdogan.registerlocation.whatsapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyyuperdogan.registerlocation.whatsapp.Adapter.UsersAdapter
import com.eyyuperdogan.registerlocation.whatsapp.ChatActivity
import com.eyyuperdogan.registerlocation.whatsapp.Models.User
import com.eyyuperdogan.registerlocation.whatsapp.databinding.FragmentChatsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var binding: FragmentChatsBinding
private lateinit var arrayList: ArrayList<User.User>
private lateinit var db: FirebaseFirestore
private lateinit var postAdapter:UsersAdapter
class ChatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentChatsBinding.inflate(layoutInflater)

        db= Firebase.firestore
        arrayList=ArrayList<User.User>()





        db.collection("NumberPhoneRegister").addSnapshotListener { value, error ->
            if (error!=null) {
                Toast.makeText(context,error.localizedMessage, Toast.LENGTH_LONG).show()
            }
            else{
                if (value!=null){
                    if (!value.isEmpty){
                        val documants=value.documents
                        //casting
                        for (document in documants){
                            val name=document.get("adı") as String
                            val sorname=document.get("soyadı") as String
                            val lastMassage=document.get("telefon numarası") as String
                            val url=document.get("downloadurl") as String
                            val user=User.User(name,sorname,lastMassage,url)
                            arrayList.add(user)
                        }
                        //veriler güncelensin
                        postAdapter.notifyDataSetChanged()


                    }
                }
            }
        }


        binding.chatsUserRecyclerView.layoutManager=LinearLayoutManager(context)
        postAdapter= UsersAdapter(arrayList)
        binding.chatsUserRecyclerView.adapter= postAdapter


        return binding.root


    }



}