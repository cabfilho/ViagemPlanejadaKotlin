package com.example.beto.viagemplanejada.database

import com.example.beto.viagemplanejada.model.Publicacao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat

object FireBaseData {
    internal var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var publicacao: Publicacao

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
    fun insertPublicacao(publicacao: Publicacao){
        val databaseRef = firebaseDatabase.reference

        databaseRef.child("Publicacao").push().setValue(publicacao)

    }
    fun updatePublicacao(id: String,publicacao: Publicacao){
        val databaseRef = firebaseDatabase.reference

        databaseRef.child("Publicacao").child(id).setValue(publicacao)

    }

    fun removePublicacao(id:String){

        val ref= firebaseDatabase
                .getReference("Publicacao")
                .child(id!!)
        ref.removeValue()
    }
}


