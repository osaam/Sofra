package com.osamaelsh3rawy.otlop.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String BASE_URL = "http://ipda3-tech.com/sofra-v2/api/v2/";
    public static Retrofit retrofit = null;

    public static ApiServies getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiServies.class);
    }

}