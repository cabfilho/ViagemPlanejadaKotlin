package com.example.beto.viagemplanejada;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {

        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public PaisService getPaisService() {
        return this.retrofit.create(PaisService.class);
    }
}
