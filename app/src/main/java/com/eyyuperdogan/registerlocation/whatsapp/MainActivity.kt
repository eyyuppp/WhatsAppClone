package com.eyyuperdogan.registerlocation.whatsapp
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.eyyuperdogan.registerlocation.whatsapp.Adapter.ViewPagerAdapter
import com.eyyuperdogan.registerlocation.whatsapp.Fragments.CallsFragment
import com.eyyuperdogan.registerlocation.whatsapp.Fragments.ChatsFragment
import com.eyyuperdogan.registerlocation.whatsapp.Fragments.StatusFragment
import com.eyyuperdogan.registerlocation.whatsapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private lateinit var binding: ActivityMainBinding
private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore: FirebaseFirestore


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()




        setUpTableLayaout()







    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.settings){
            Toast.makeText(this,"settings giriş yapıldı", Toast.LENGTH_LONG).show()

        }
        else if (item.itemId==R.id.logout){
            auth.signOut()
            intent= Intent(this,Login::class.java)
            startActivity(intent)
            Toast.makeText(this,"Hesabınızdan çıkış yapıldı", Toast.LENGTH_LONG).show()


        }
        else if (item.itemId==R.id.groupChat){

        }

        return super.onContextItemSelected(item)
    }




    fun setUpTableLayaout(){
        val adapter= ViewPagerAdapter(supportFragmentManager)
        adapter.addFragmant(ChatsFragment(),"chats")
        adapter.addFragmant(StatusFragment(),"status")
        adapter.addFragmant(CallsFragment(),"calls")
        binding.viewPager.adapter=adapter
        binding.tableLayout.setupWithViewPager(binding.viewPager)


        binding.tableLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_chat_24)
        binding.tableLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_data_status_24)
        binding.tableLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_add_ic_call_24)




    }
}