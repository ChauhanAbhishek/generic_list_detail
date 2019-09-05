package com.example.listdetailapplication;

import com.example.listdetailapplication.list.ListResponseApi;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET(".")
    Single<Response<ListResponseApi>> searchMovies(@Query("apikey") String apiKey,
                                                @Query("s") String searchQuery,
                                                @Query("page") String page);

    @GET(".")
    Single<Response<ListResponseApi>> getMovieDetail(
            @Query("apikey") String apikey,@Query("i") String i);


//    @GET("{urlId}")
//    Single<Response<ListResponseApi>> getResponseAtPath(@Path("urlId") String urlId);
}
