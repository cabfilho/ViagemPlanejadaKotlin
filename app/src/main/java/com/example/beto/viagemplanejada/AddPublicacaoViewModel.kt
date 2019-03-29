package com.example.beto.viagemplanejada

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.PrimaryKey
import com.example.beto.viagemplanejada.model.Publicacao
import io.github.mobileteacher.newsrevision.api.RetrofitProvider
import io.github.mobileteacher.newsrevision.models.News
import io.github.mobileteacher.newsrevision.models.NewsResponseObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddPublicacaoViewModel(application: Application): AndroidViewModel(application) {
    val date = MutableLiveData<Date>()


    val pais: String = ""
    val cidade: String = ""
    val rating: Float = 0.toFloat()

    val id:String=""

    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val shouldFinish = MutableLiveData<Boolean>()

    val repository = ViagemRepository(application.applicationContext)

    fun addPublicacao(){
        val newsDate = date.value ?: ""
        val publicacao = Publicacao(pais,
            cidade,
            newsText,
            newsInformative,
            newsDate)

        constructor(pais: String, cidade: String, dtViagem: Date, dtPublicacao: Date, rating: Float) {
            this.pais = pais
            this.cidade = cidade
            this.dtViagem = dtViagem
            this.dtPublicacao = dtPublicacao
            this.rating = rating
        }
        isLoading.value = true
        repository.insertNews(news, {
            isLoading.value = false
            shouldFinish.value = true
        }, {
            isLoading.value = false
        })



}