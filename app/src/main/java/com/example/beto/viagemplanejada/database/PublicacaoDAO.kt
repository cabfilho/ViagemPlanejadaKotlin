package com.example.beto.viagemplanejada.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beto.viagemplanejada.model.Publicacao

@Dao
interface PublicacaoDAO {

    @Query("SELECT * FROM Publicacao")
    fun getAllPublicacoes(): LiveData<List<Publicacao>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(publicacao: Publicacao)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newsList: List<Publicacao>)

    @Update()
    fun update(publicacao: Publicacao)

    @Delete()
    fun delete(publicacao: Publicacao)


}