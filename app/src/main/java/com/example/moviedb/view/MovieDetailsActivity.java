package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.GenreAdapter;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView movdetail_txt_title, movdetail_txt_year, movdetail_txt_score, movdetail_txt_tagline
            , movdetail_txt_orititle, movdetail_txt_director, movdetail_txt_releasedate, movdetail_txt_overview;
    private ImageView movdetail_img_background, movdetail_img_poster, movdetail_img_back;
    private RecyclerView movdetail_rv_genre;
    private RatingBar movdetail_ratingbar_score;

    private String movie_id = "", title = "", releasedate = "", year = "", score = "", tagline = "", orititle = "", director = "", overview = ""
            , POSTER_FULL_PATH = "", BACKGROUND_FULL_PATH = "";
    private MovieViewModel viewModel;
    private Intent intent;
    private GenreAdapter genreAdapter;

    private Observer<Credits> showCreditsByIdResult = new Observer<Credits>() {
        @Override
        public void onChanged(Credits credits) {

            //Proses to get DIRECTOR from CREW
            List<Credits.Crew> crewList = credits.getCrew();
            for(int i = 0; i < crewList.size() ; i++){
                if(credits.getCrew().get(i).getJob().equalsIgnoreCase("director")) {
                    director += credits.getCrew().get(i).getName() + "   ";


                }
            }
            //Assign to XML
            movdetail_txt_director.setText(director);
        }
    };

    private Observer<Movies> showMovieByIdResult = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            //Proses to get YEAR from RELEASE DATE

            //Assign to Variables
            title = movies.getTitle();
            releasedate = movies.getRelease_date();
            year ="-";
            score = String.valueOf(movies.getVote_average());
            float score5 = Float.valueOf(score)/2;
            tagline = movies.getTagline();
            orititle = movies.getOriginal_title();
            overview = movies.getOverview();
            POSTER_FULL_PATH = Const.IMG_PATH+movies.getPoster_path().toString();
            BACKGROUND_FULL_PATH = Const.IMG_PATH+movies.getBackdrop_path();

            // Set to XML
            movdetail_txt_title.setText(title);
            movdetail_txt_tagline.setText(tagline);
            movdetail_txt_year.setText(year);
            movdetail_txt_score.setText(score);
            movdetail_txt_orititle.setText(orititle);
            movdetail_txt_overview.setText(overview);
            movdetail_txt_releasedate.setText(releasedate);
            movdetail_txt_overview.setText(overview);
            movdetail_ratingbar_score.setRating(score5);

            Glide.with(MovieDetailsActivity.this).load(POSTER_FULL_PATH).into(movdetail_img_poster);
            Glide.with(MovieDetailsActivity.this).load(BACKGROUND_FULL_PATH).into(movdetail_img_background);

            movdetail_rv_genre.setLayoutManager(new GridLayoutManager(MovieDetailsActivity.this, 1, RecyclerView.HORIZONTAL, false));
            genreAdapter.setGenresList(movies.getGenres());
            movdetail_rv_genre.setAdapter(genreAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initView();
        setListener();

        movie_id = intent.getStringExtra("movie_id");
        viewModel.getCreditsById(movie_id);
        viewModel.getResultGetCreditsById().observe(MovieDetailsActivity.this, showCreditsByIdResult);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showMovieByIdResult);

    }

    private void setListener(){
        movdetail_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        //Others
        genreAdapter = new GenreAdapter(MovieDetailsActivity.this);
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        intent = getIntent();

        //Text
        movdetail_txt_title = findViewById(R.id.movdetail_txt_title);
        movdetail_txt_year = findViewById(R.id.movdetail_txt_year);
        movdetail_txt_score = findViewById(R.id.movdetail_txt_score);
        movdetail_txt_tagline = findViewById(R.id.movdetail_txt_tagline);
        movdetail_txt_orititle = findViewById(R.id.movdetail_txt_orititle);
        movdetail_txt_director = findViewById(R.id.movdetail_txt_director);
        movdetail_txt_releasedate = findViewById(R.id.movdetail_txt_releasedate);
        movdetail_txt_overview = findViewById(R.id.movdetail_txt_overview);

        //Recycler View
        movdetail_rv_genre = findViewById(R.id.movdetail_rv_genre);

        //Rating Bar
        movdetail_ratingbar_score = findViewById(R.id.movdetail_ratingbar_score);

        //Image
        movdetail_img_background = findViewById(R.id.movdetail_img_background);
        movdetail_img_poster = findViewById(R.id.movdetails_img_poster);
        movdetail_img_back = findViewById(R.id.movdetail_img_back);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}