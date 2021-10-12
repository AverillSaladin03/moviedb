package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.viewmodel.MovieViewModel;

public class NowPlayingActivity extends AppCompatActivity {

    private RecyclerView nowplaying_rv_nowplayinglist;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        nowplaying_rv_nowplayinglist = findViewById(R.id.nowplaying_rv_nowplayinglist);

        viewModel = new ViewModelProvider(NowPlayingActivity.this).get(MovieViewModel.class);
        viewModel.getNowPlaying();
        viewModel.getResultNowPlaying().observe(NowPlayingActivity.this, showNowPlayingResult);
    }

    private Observer<NowPlaying> showNowPlayingResult = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            nowplaying_rv_nowplayinglist.setLayoutManager(new LinearLayoutManager(NowPlayingActivity.this));
            NowPlayingAdapter adapter = new NowPlayingAdapter(NowPlayingActivity.this);
            adapter.setListNowPlaying(nowPlaying.getResults());
            nowplaying_rv_nowplayinglist.setAdapter(adapter);
        }
    };


}