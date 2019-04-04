package com.example.beto.viagemplanejada.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beto.viagemplanejada.model.Publicacao

@Dao
interface PublicacaoDAO {

    @Query("SELECT * FROM Publicacao")
    fun getAllPublicacoes(): LiveData<MutableList<Publicacao>>

    @Query("select * from Publicacao where id =:id")
    fun getPublicacao(id:Int): Publicacao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(publicacao: Publicacao): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(publicacaoList: List<Publicacao>)

    @Update()
    fun update(publicacao: Publicacao)

    @Delete()
    fun delete(publicacao: Publicacao)


}