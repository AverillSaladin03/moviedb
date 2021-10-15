package com.example.moviedb.retrofit;

import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.TopRated;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {
    // !-Tempat dimana inisialisasi URL tujuan, dan data yang dilempar-!

    @GET("movie/{movie_id}")
    Call<Movies> getMovieById(
            @Path("movie_id") String movieId, //di link ada /
            @Query("api_key") String apiKey //di link ada ?
            //ex : https://api.themoviedb.org/3/movie/550?api_key=da413b80c5ccb0aefa4b77d91c8ff852
            // Path : /movie/550
            // Query : ?api_key=da413b80c5ccb0aefa4b77d91c8ff852
    );

    @GET("movie/top_rated")
    Call<TopRated> getTopRatedMovie(
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/credits")
    Call<Credits> getCreditsById(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/now_playing")
    Call<NowPlaying> getNowPlaying(
            @Query("api_key") String apiKey
    );

}
