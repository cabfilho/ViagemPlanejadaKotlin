package com.example.beto.viagemplanejada

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.beto.viagemplanejada.database.FireBaseData
import com.example.beto.viagemplanejada.model.Publicacao
import com.example.beto.viagemplanejada.database.PublicacaoDAO
import com.example.beto.viagemplanejada.database.ViagemDatabase
import com.example.beto.viagemplanejada.services.PaisService
import com.example.beto.viagemplanejada.services.RetrofitConfig
import io.github.mobileteacher.newsrevision.api.NewsAPI
import io.github.mobileteacher.newsrevision.api.RetrofitProvider
import io.github.mobileteacher.newsrevision.models.News
import io.github.mobileteacher.newsrevision.models.NewsResponseObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViagemRepository(context: Context) {

    val newsDAO: PublicacaoDAO = ViagemDatabase.get(context).publicacaoDao()


    fun insertPublicacao(publicacao: Publicacao){
        newsDAO.insert(publicacao)
        FireBaseData.insertPublicacao(publicacao)
    }

    fun allNews():LiveData<List<News>>{
        val newsList = newsDAO.getAllNews()
        // verificar critério de validade do cache
        val shouldCache = newsList.value?.isEmpty() ?: true
        if (shouldCache){
            cacheData()
        }
        return newsList
    }

    fun cacheData(){
        val call = newsApi.getAllNews()
        call.enqueue(object : Callback<NewsResponseObject>{
            override fun onFailure(call: Call<NewsResponseObject>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<NewsResponseObject>, response: Response<NewsResponseObject>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        newsDAO.insertAll(it.news)
                    }
                }
            }

        })
    }

}