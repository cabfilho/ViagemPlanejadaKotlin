package com.example.beto.viagemplanejada

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.example.beto.viagemplanejada.model.Publicacao




class AddPublicacaoViewModel(application: Application): AndroidViewModel(application) {
    val dtViagem = MutableLiveData<String>()
    val dtPublicacao = MutableLiveData<String>()

    val pais: String = ""
    val cidade: String = ""
    val rating: Float = 0.toFloat()

    val id:String=""

    val errorMessage = MutableLiveData<String>()
    val isLoading =    MutableLiveData<Boolean>().apply { value = false }
    val shouldFinish = MutableLiveData<Boolean>()

    val repository = ViagemRepository(application.applicationContext)

    fun addPublicacao() {
        val dtViagemConvert = dtViagem.value ?: ""
        val dtPublicacaoConvert = dtPublicacao.value ?: ""
        val publicacao = Publicacao(pais,
                cidade,
                dtViagemConvert,
                dtPublicacaoConvert,
                rating)
        isLoading.value = true
        repository.insertPublicacao(publicacao, {
            isLoading.value = false
            shouldFinish.value = true
        }, {
            isLoading.value = false
        })

    }






}