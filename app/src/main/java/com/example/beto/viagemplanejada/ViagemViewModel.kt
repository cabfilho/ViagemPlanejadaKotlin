package com.example.beto.viagemplanejada

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.MutableLiveData


class ViagemViewModel(application: Application): AndroidViewModel(application) {

    private var _errorMessage:String = ""
    set(value) {
        field = value
        errorMessage.value = value
    }
    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val isLoading = MutableLiveData<Boolean>()

    val repository = ViagemRepository(application.applicationContext)
    val publicacaoList = repository.allPublicacoes()



}