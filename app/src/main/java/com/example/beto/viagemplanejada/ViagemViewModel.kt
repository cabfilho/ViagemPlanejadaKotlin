package com.example.beto.viagemplanejada

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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