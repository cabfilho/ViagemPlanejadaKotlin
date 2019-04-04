package com.example.beto.viagemplanejada.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.beto.viagemplanejada.model.Publicacao


@Database(entities = [Publicacao::class], version = 5)
abstract class ViagemDatabase: RoomDatabase() {

    abstract fun publicacaoDao(): PublicacaoDAO

    companion object {
        var INSTANCE: ViagemDatabase? = null
        fun get(context: Context): ViagemDatabase{
            return INSTANCE ?: run {
                val instance = Room.databaseBuilder(
                    context,
                    ViagemDatabase::class.java,
                    "PublicacaoDatabase"
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}