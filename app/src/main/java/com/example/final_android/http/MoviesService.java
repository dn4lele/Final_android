package com.example.final_android.http;

import com.example.final_android.httpObjects.DetailMovieInfo;
import com.example.final_android.httpObjects.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {
    @GET("movies")
    public Call<Root> getMovies(
            @Query("page") Integer page,
            @Query("q") String q,
            @Query("skip") Integer skip
    );

    @GET("genres/6/movies")
    public Call<Root> getAdventureMovies(
            @Query("page") Integer page
    );


    //get movie by id the route is movies/{movie_id}
    //i want to add param not Query



    @GET("movies/{slug}")
    public Call<DetailMovieInfo> getMovieById(
        @Path("slug") String slug
    );


    @GET("movies")
    public Call<Root> getMoviesByName(
            @Query("q") String q,
            @Query("page") Integer page
    );


}
