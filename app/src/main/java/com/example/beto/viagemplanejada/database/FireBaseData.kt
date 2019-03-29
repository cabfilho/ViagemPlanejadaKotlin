package com.example.beto.viagemplanejada.database

import android.view.View
import com.example.beto.viagemplanejada.R
import com.example.beto.viagemplanejada.model.Publicacao
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat



object FireBaseData {
    internal var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var publicacao: Publicacao

    fun getAllPublicacao(): MutableList<Publicacao> {
        var publicacacoes: MutableList<Publicacao> = arrayListOf()
        firebaseDatabase.reference.child("Publicacao").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    for (snapShot in dataSnapshot.children) {
                        publicacacoes.add(snapShot.getValue<Publicacao>(Publicacao::class.java!!)!!)
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        return publicacacoes
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

        databaseRef.child("Publicacao").child(publicacao.id).setValue(publicacao)

    }


    fun removePublicacao(id:String){

        val ref= firebaseDatabase
                .getReference("Publicacao")
                .child(id!!)
        ref.removeValue()
    }
}


