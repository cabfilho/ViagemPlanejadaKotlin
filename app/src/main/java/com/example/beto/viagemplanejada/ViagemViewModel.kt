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
    val newsList = repository.allNews()


//    fun getData(){
        //newsList = repository.allNews()
//        val call = RetrofitProvider.newsAPI.getAllNews()
//        isLoading.value = true
//        call.enqueue(object : Callback<NewsResponseObject> {
//            override fun onFailure(call: Call<NewsResponseObject>, t: Throwable) {
//                _errorMessage = "Deu ruim: ${t.message}"
//                isLoading.value = false
//            }
//
//            override fun onResponse(call: Call<NewsResponseObject>,
//                                    response: Response<NewsResponseObject>
//            ) {
//                if (response.isSuccessful){
//                    response.body()?.let {newsResponseObject->
//                        newsList.value = newsResponseObject.news
//                    }
//                } else {
//                    _errorMessage = "Deu ruim, fale com o admin"
//                }
//                isLoading.value = false
//            }
//
//        })
//    }
}