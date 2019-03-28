package com.example.beto.viagemplanejada

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

import android.os.Bundle

import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    internal lateinit var viagemRecyclerView: RecyclerView
    internal lateinit var databaseRef: DatabaseReference
    internal lateinit var listener: ChildEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viagemRecyclerView = findViewById(R.id.viagemRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        val firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRef = firebaseDatabase.getReference("Publicacao")
        val adapter = ViagemAdapter(ArrayList())
        viagemRecyclerView.adapter = adapter
        val lm = LinearLayoutManager(applicationContext)
        viagemRecyclerView.layoutManager = lm

        viagemRecyclerView.addItemDecoration(
                DividerItemDecoration(applicationContext,
                        DividerItemDecoration.VERTICAL))

        progressBar!!.visibility = View.VISIBLE
        val context = this
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var ni: NetworkInfo? = null
        if (cm != null) {
            ni = cm.activeNetworkInfo

        }
        if (ni != null && ni.isConnected) {
            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.exists()) {

                    } else {
                        Snackbar.make(findViewById(R.id.root),
                                "Insira uma publicação",
                                Snackbar.LENGTH_LONG).show()
                    }

                    progressBar!!.visibility = View.GONE
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
            listener = object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val adapter = viagemRecyclerView.adapter as ViagemAdapter
                    adapter.addItem(dataSnapshot)
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                    val adapter = viagemRecyclerView.adapter as ViagemAdapter
                    adapter.changeItem(dataSnapshot)
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    val adapter = viagemRecyclerView.adapter as ViagemAdapter
                    adapter.removeItem(dataSnapshot)
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }

            databaseRef.addChildEventListener(listener)
        } else {
            Snackbar.make(findViewById(R.id.root),
                    "Conecte a Internet",
                    Snackbar.LENGTH_LONG).show()
            progressBar!!.visibility = View.GONE

        }

    }

    fun addPublicacao(view: View) {
        val intent = Intent(this, AddPublicacao::class.java)
        startActivity(intent)
    }
}
