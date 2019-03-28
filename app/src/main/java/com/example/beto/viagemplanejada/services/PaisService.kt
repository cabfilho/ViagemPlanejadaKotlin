package com.example.beto.viagemplanejada.services

import com.example.beto.viagemplanejada.model.Pais
import retrofit2.Call
import retrofit2.http.GET

interface PaisService {
    @GET("v2/all")
    fun buscarPais(): Call<List<Pais>>
}
