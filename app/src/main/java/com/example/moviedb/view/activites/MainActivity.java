package com.example.moviedb.view.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private Button main_btn_hit;
    private TextView main_txt_judul, main_txt_date, main_txt_overview, main_txt_status;
    private TextInputLayout main_inptxt_id;
    private ImageView main_img_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        viewModel = new ViewModelProvider(MainActivity.this).get(MovieViewModel.class);


        main_img_movie = findViewById(R.id.main_img_movie);
        main_inptxt_id = findViewById(R.id.main_inptxt_id);
        main_txt_judul = findViewById(R.id.main_txt_judul);
        main_txt_status = findViewById(R.id.main_txt_status);
        main_txt_overview = findViewById(R.id.main_txt_overview);
        main_txt_date = findViewById(R.id.main_txt_date);
        main_btn_hit = findViewById(R.id.main_btn_hit);
        main_btn_hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieID = main_inptxt_id.getEditText().getText().toString();
                if(movieID == null){
                    main_inptxt_id.setError("Please fill up with an Id Number");
                }else {
                    //Sao Progressive : 761898
                    viewModel.getMovieById(movieID);
                    viewModel.getResultGetMovieById().observe(MainActivity.this, showResultMovies);
                }
            }
        });
    }
    //Observer
    private Observer<Movies> showResultMovies = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String Full_Img_Path = Const.IMG_PATH + movies.getPoster_path().toString();

            Glide.with(MainActivity.this).load(Full_Img_Path).into(main_img_movie);
            main_txt_judul.setText(movies.getTitle());
            main_txt_date.setText("Release On : " + movies.getRelease_date());
            main_txt_status.setText("Status : " + movies.getStatus());
            main_txt_overview.setText(movies.getOverview());
        }
    };
}