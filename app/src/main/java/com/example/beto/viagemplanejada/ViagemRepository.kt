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
    val fireBaseData: FireBaseData = FireBaseData()
    fun insertPublicacao(publicacao: Publicacao,succesAction: ()->Unit, failureAction: ()->Unit){
        var id:Long = 0
        id = publicacaoDAO.insert(publicacao)
        publicacao.id = id.toInt()
        var ni: NetworkInfo? = null
        if (cm != null) {
            ni = cm.activeNetworkInfo

        }

        if (ni != null && ni.isConnected) {
            fireBaseData.savePublicacao(publicacao)
            succesAction()
        }else{
            failureAction()
        }
    }
    fun getPublicacao(id:String):Publicacao{
        var publicacao:Publicacao
        publicacao = publicacaoDAO.getPublicacao(id.toInt())
        if(publicacao==null){

            var ni: NetworkInfo? = null

            if (cm != null) {
                ni = cm.activeNetworkInfo

            }
            if (ni != null && ni.isConnected) {
                publicacao= fireBaseData.getPublicacao(id)

            }

        }
        return publicacao
    }

    fun removePublicacao(publicacao: Publicacao){
        publicacaoDAO.delete(publicacao)

        var ni: NetworkInfo? = null
        if (cm != null) {
            ni = cm.activeNetworkInfo

        }

        if (ni != null && ni.isConnected) {
            fireBaseData.removePublicacao(publicacao.id)
        }
    }

    fun updatePublicacao(publicacao: Publicacao){
        publicacaoDAO.update(publicacao)

        var ni: NetworkInfo? = null
        if (cm != null) {
            ni = cm.activeNetworkInfo

        }

        if (ni != null && ni.isConnected) {
            fireBaseData.savePublicacao(publicacao)
        }
    }

    fun allPublicacoes():LiveData<MutableList<Publicacao>>{
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
            fireBaseData.getAllPublicacao()

            if(fireBaseData.publicacacoes.isNotEmpty())
                publicacaoDAO.insertAll(fireBaseData.publicacacoes)
        }
    }

}