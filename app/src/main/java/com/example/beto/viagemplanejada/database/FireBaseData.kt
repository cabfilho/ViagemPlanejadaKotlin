package com.example.beto.viagemplanejada.database

import android.util.Log
import android.view.View
import com.example.beto.viagemplanejada.R
import com.example.beto.viagemplanejada.model.Publicacao
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import java.text.SimpleDateFormat



class FireBaseData {
    internal var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var publicacao: Publicacao
    var publicacacoes: MutableList<Publicacao> = arrayListOf()
    fun getAllPublicacao(){

        firebaseDatabase.reference.child("Publicacao").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    Log.i("snapShot","Passou aqui")
                    var i = 0
                    for (snapShot in dataSnapshot.children) {
                        i++
                        Log.i("snapShot","Passou aqui + " + i)
                        if(snapShot!=null) {
                            snapShot.getValue<Publicacao>(Publicacao::class.java)?.let { publicacacoes.add(it) }
                        }
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    fun getPublicacao(id:String): Publicacao{

        val databaseRef = firebaseDatabase.reference.child("Publicacao").child(id)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    publicacao = dataSnapshot.getValue<Publicacao>(Publicacao::class.java!!)!!

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        return publicacao
    }
    fun savePublicacao(publicacao: Publicacao){
        val databaseRef = firebaseDatabase.reference

        databaseRef.child("Publicacao").child(publicacao.id.toString()).setValue(publicacao)

    }


    fun removePublicacao(id:Int){

        val ref= firebaseDatabase
                .getReference("Publicacao")
                .child(id.toString())
        ref.removeValue()
    }
}


