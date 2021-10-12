package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

import retrofit2.http.POST;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView movdetail_txt_title, movdetail_txt_year, movdetail_txt_score, movdetail_txt_tagline, movdetail_txt_genres
            , movdetail_txt_orititle, movdetail_txt_director, movdetail_txt_releasedate, movdetail_overview;
    private ImageView movdetail_img_background, movdetail_img_poster;

    private String movie_id = "", title = "", releasedate = "", year = "", score = "", tagline = "", orititle = "", director = "", overview = ""
            , POSTER_FULL_PATH = "", BACKGROUND_FULL_PATH = "", genres = "";
    private MovieViewModel viewModel;

    private Observer<Movies> showMovieByIdResult = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            //Proses to get YEAR from RELEASE DATE

            //Proses to get GENRE from GENRES
            for(int i = 0; i < movies.getGenres().size(); i++){
                if(i == movies.getGenres().size()-1) {
                    genres += movies.getGenres().get(i).getName();
                }else{
                    genres += movies.getGenres().get(i).getName() + ", ";
                }
            }

            //Assign to Variables
            title = movies.getTitle();
            releasedate = movies.getRelease_date();
            year ="-";
            score = String.valueOf(movies.getVote_average());
            tagline = movies.getTagline();
            orititle = movies.getOriginal_title();
            director = movies.get;
            overview = movies.getOverview();
            POSTER_FULL_PATH = Const.IMG_PATH+movies.getPoster_path().toString();
            BACKGROUND_FULL_PATH = Const.IMG_PATH+movies.getBackdrop_path();

            // Set to XML
            movdetail_txt_title.setText(title);
            movdetail_txt_tagline.setText(tagline);
            movdetail_txt_year.setText(year);
            movdetail_txt_score.setText(score);
            movdetail_txt_genres.setText(genres);
            movdetail_txt_orititle.setText(orititle);
            movdetail_overview.setText(overview);
            movdetail_txt_releasedate.setText(releasedate);
            movdetail_overview.setText(overview);

            Glide.with(MovieDetailsActivity.this).load(POSTER_FULL_PATH).into(movdetail_img_poster);
            Glide.with(MovieDetailsActivity.this).load(BACKGROUND_FULL_PATH).into(movdetail_img_background);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initView();
        
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        Intent intent = getIntent();

        movie_id = intent.getStringExtra("movie_id");
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showMovieByIdResult);

    }

    private void initView() {
        //Text
        movdetail_txt_title = findViewById(R.id.movdetail_txt_title);
        movdetail_txt_year = findViewById(R.id.movdetail_txt_year);
        movdetail_txt_score = findViewById(R.id.movdetail_txt_score);
        movdetail_txt_tagline = findViewById(R.id.movdetail_txt_tagline);
        movdetail_txt_genres = findViewById(R.id.movdetail_txt_genre);
        movdetail_txt_orititle = findViewById(R.id.movdetail_txt_orititle);
        movdetail_txt_director = findViewById(R.id.movdetail_txt_director);
        movdetail_txt_releasedate = findViewById(R.id.movdetail_txt_releasedate);
        movdetail_overview = findViewById(R.id.movdetail_txt_overview);

        //Image
        movdetail_img_background = findViewById(R.id.movdetail_img_background);
        movdetail_img_poster = findViewById(R.id.movdetails_img_poster);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}