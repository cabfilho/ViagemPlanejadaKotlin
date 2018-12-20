package com.example.beto.viagemplanejada;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {

        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public PaisService getPaisService() {
        return this.retrofit.create(PaisService.class);
    }
}
