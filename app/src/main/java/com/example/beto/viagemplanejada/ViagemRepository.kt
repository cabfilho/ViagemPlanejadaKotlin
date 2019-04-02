package com.example.beto.viagemplanejada

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import com.example.beto.viagemplanejada.database.FireBaseData
import com.example.beto.viagemplanejada.model.Publicacao
import com.example.beto.viagemplanejada.database.PublicacaoDAO
import com.example.beto.viagemplanejada.database.ViagemDatabase


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViagemRepository(context: Context) {

    val publicacaoDAO: PublicacaoDAO = ViagemDatabase.get(context).publicacaoDao()
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun insertPublicacao(publicacao: Publicacao,succesAction: ()->Unit, failureAction: ()->Unit){
        publicacaoDAO.insert(publicacao)

        var ni: NetworkInfo? = null
        if (cm != null) {
            ni = cm.activeNetworkInfo

        }

        if (ni != null && ni.isConnected) {
            FireBaseData.savePublicacao(publicacao)
            succesAction()
        }else{
            failureAction()
        }
    }


    fun removePublicacao(publicacao: Publicacao){
        publicacaoDAO.delete(publicacao)

        var ni: NetworkInfo? = null
        if (cm != null) {
            ni = cm.activeNetworkInfo

        }

        if (ni != null && ni.isConnected) {
            FireBaseData.removePublicacao(publicacao.id)
        }
    }

    fun updatePublicacao(publicacao: Publicacao){
        publicacaoDAO.update(publicacao)

        var ni: NetworkInfo? = null
        if (cm != null) {
            ni = cm.activeNetworkInfo

        }

        if (ni != null && ni.isConnected) {
            FireBaseData.savePublicacao(publicacao)
        }
    }

    fun allPublicacoes():LiveData<List<Publicacao>>{
        val allPublicacoes = publicacaoDAO.getAllPublicacoes()
        // verificar critério de validade do cache
        val shouldCache = allPublicacoes.value?.isEmpty() ?: true
        if (shouldCache){
            cacheData()
        }
        return allPublicacoes
    }

    fun cacheData(){
        var ni: NetworkInfo? = null
        if (cm != null) {
            ni = cm.activeNetworkInfo

        }

        if (ni != null && ni.isConnected) {
            val publicacoes = FireBaseData.getAllPublicacao()
            if(publicacoes.isNotEmpty())
                publicacaoDAO.insertAll(publicacoes)
        }
    }

}