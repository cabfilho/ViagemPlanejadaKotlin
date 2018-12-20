package com.example.beto.viagemplanejada;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaisService {
    @GET("v2/all")
    Call<List<Pais>> buscarPais();
}
