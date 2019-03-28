package com.example.beto.viagemplanejada.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private val retrofit: Retrofit

    val paisService: PaisService
        get() = this.retrofit.create(PaisService::class.java!!)

    init {

        this.retrofit = Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
