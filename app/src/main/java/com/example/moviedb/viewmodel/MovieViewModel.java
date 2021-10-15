package com.example.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.TopRated;
import com.example.moviedb.repository.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = MovieRepository.getInstance();
    }

    //== Begin of View Model Get Movie by Id
    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();

    public void getMovieById(String movieId){
        resultGetMovieById = mRepository.getMovieData(movieId);
    }
    public LiveData<Movies> getResultGetMovieById (){
        return resultGetMovieById;
    }
    //== END

    //== Begin of View Model Get Now Playing
    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();

    public void getNowPlaying(){
        resultGetNowPlaying = mRepository.getNowPlayingData();
    }
    public LiveData <NowPlaying> getResultNowPlaying (){
        return resultGetNowPlaying;
    }
    //== END

    //== Begin of View Model Get Credits By ID
    private MutableLiveData<Credits> resultGetCreditsById = new MutableLiveData<>();

    public void getCreditsById(String movieId){
        resultGetCreditsById = mRepository.getCreditsData(movieId);
    }
    public LiveData <Credits> getResultGetCreditsById (){
        return resultGetCreditsById;
    }
    //== END

    //== Begin of View Model Get All Movie
    private MutableLiveData<TopRated> resultGetTopRatedMovies = new MutableLiveData<>();

    public void getTopRatedMovies(){
        resultGetTopRatedMovies = mRepository.getAllMovie();
    }
    public LiveData<TopRated> getResultGetTopRatedMovies(){
        return resultGetTopRatedMovies;
    }
    //== END

}
