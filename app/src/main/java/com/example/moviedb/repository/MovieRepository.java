package com.example.moviedb.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.TopRated;
import com.example.moviedb.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository mRepository;

    private MovieRepository(){}

    public static MovieRepository getInstance(){
        if(mRepository == null){
            mRepository = new MovieRepository();
        }
        return mRepository;
    }

    //Main Activity, GET MOVIE
    public MutableLiveData<Movies> getMovieData (String movieId){
        final MutableLiveData<Movies> result = new MutableLiveData<>();

        ApiService.endPoint().getMovieById(movieId, Const.API_KEY).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });

        return result;
    }


    //Now Playing
    public MutableLiveData<NowPlaying> getNowPlayingData (){
        final MutableLiveData<NowPlaying> result = new MutableLiveData<>();

        ApiService.endPoint().getNowPlaying(Const.API_KEY).enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });

        return result;
    }

    //Credits
    public MutableLiveData<Credits> getCreditsData (String movieId) {
        final MutableLiveData<Credits> result = new MutableLiveData<>();
        ApiService.endPoint().getCreditsById(movieId, Const.API_KEY).enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {

            }
        });
        return result;
    }

    //GET ALL MOVIE
    public MutableLiveData<TopRated> getAllMovie (){
        final MutableLiveData<TopRated> result = new MutableLiveData<>();

        ApiService.endPoint().getTopRatedMovie(Const.API_KEY).enqueue(new Callback<TopRated>() {
            @Override
            public void onResponse(Call<TopRated> call, Response<TopRated> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TopRated> call, Throwable t) {

            }
        });

        return result;
    }
}
