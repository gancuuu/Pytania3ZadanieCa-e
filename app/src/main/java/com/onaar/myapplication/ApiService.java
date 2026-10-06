package com.onaar.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("gancuuu/Pytania_2/main/db.json")
    Call<WydarzenieResponse> pobierzWydarzenia();
}
