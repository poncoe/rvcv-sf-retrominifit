package com.poncoe.retrofit.recyclerviewsearchfilterexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("data-json-ips.php")
    Call<List<Movie>> getMovies();
}
