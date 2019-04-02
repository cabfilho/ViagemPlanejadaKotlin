package com.example.beto.viagemplanejada

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo

import android.os.Bundle

import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.beto.viagemplanejada.model.Publicacao
import com.google.android.material.snackbar.Snackbar

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null


 //   internal lateinit var listener: ChildEventListener

    private lateinit var viagemViewModel: ViagemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viagemViewModel = ViewModelProviders.of(this)
                .get(ViagemViewModel::class.java)

        setupRecyclerView()
        subscribe()

    }

    fun addPublicacao(view: View) {
        val intent = Intent(this, AddPublicacao::class.java)
        startActivity(intent)
    }

    private fun setupRecyclerView(){


        progressBar = findViewById(R.id.progressBar)

        val adapter = ViagemAdapter(emptyList<Publicacao>() as MutableList<Publicacao>)

        viagemRecyclerView.adapter = adapter

        val lm = LinearLayoutManager(applicationContext)

        viagemRecyclerView.layoutManager = lm

        viagemRecyclerView.addItemDecoration(
                DividerItemDecoration(applicationContext,
                        DividerItemDecoration.VERTICAL))



    }

    private fun subscribe(){
        viagemViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viagemViewModel.publicacaoList.observe(this, Observer {list->
            val adapter = viagemRecyclerView.adapter as? ViagemAdapter
            adapter?.setData(list as MutableList<Publicacao>)
        })

        viagemViewModel.isLoading.observe(this, Observer {
            if (it){
                progressBar!!.visibility = View.VISIBLE
            } else {
                progressBar!!.visibility = View.GONE
            }
        })
    }


}
