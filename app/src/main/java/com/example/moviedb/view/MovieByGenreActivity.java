package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.moviedb.R;
import com.example.moviedb.adapter.MovieByGenreAdapter;
import com.example.moviedb.model.TopRated;
import com.example.moviedb.viewmodel.MovieViewModel;

public class MovieByGenreActivity extends AppCompatActivity {

    private Toolbar genre_toolbar_title;
    private RecyclerView genre_rv_movies;
    private TextView genre_txt_announce;

    private MovieViewModel viewModel;

    private Intent intent;
    private String genreGiven;
    private int id_genreGiven;

    private Observer<TopRated> showAllMovieResult = new Observer<TopRated>() {
        @Override
        public void onChanged(TopRated topRated) {
            genre_rv_movies.setLayoutManager(new LinearLayoutManager(MovieByGenreActivity.this));
            MovieByGenreAdapter adapter = new MovieByGenreAdapter(MovieByGenreActivity.this, id_genreGiven);
            adapter.setMovieByGenresList(topRated.getResults());
            genre_rv_movies.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        initView();
        genre_toolbar_title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        genreGiven = intent.getStringExtra("genre");
        id_genreGiven = intent.getIntExtra("id_genreGiven", -1);
        genre_toolbar_title.setTitle(genreGiven);
        //Set Recycler View (Dalam pengembangan)
//        viewModel.getTopRatedMovies();
//        viewModel.getResultGetTopRatedMovies().observe(MovieByGenreActivity.this, showAllMovieResult);


    }

    private void initView() {
        viewModel = new ViewModelProvider(MovieByGenreActivity.this).get(MovieViewModel.class);

        genre_txt_announce = findViewById(R.id.genre_txt_announce);
        genre_toolbar_title = findViewById(R.id.genre_toolbar_title);
        genre_rv_movies = findViewById(R.id.genre_rv_movies);
        intent = getIntent();
    }
}