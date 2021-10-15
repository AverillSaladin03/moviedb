package com.example.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.view.MovieDetailsActivity;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.CardViewViewHolder> {

    //Variable(s)
    private Context context;
    private List<NowPlaying.Results> listNowPlaying;

    //GetArrayList
    private List<NowPlaying.Results> getListNowPlaying(){
        return listNowPlaying;
    }
    //SetArrayList
    public void setListNowPlaying(List<NowPlaying.Results> listNowPlaying){
        this.listNowPlaying = listNowPlaying;
    }
    //Constructor
    public NowPlayingAdapter (Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent,
                false);
        return new NowPlayingAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int position) {
        final  NowPlaying.Results results = getListNowPlaying().get(position);
        String FULL_IMG_PATH = Const.IMG_PATH + results.getPoster_path();
        Glide.with(context)
                .load(FULL_IMG_PATH)
                .into(holder.card_img_poster);

        holder.card_txt_title.setText(results.getTitle());
        holder.card_txt_overview.setText(results.getOverview());
        holder.card_txt_releasedate.setText(results.getRelease_date());
        holder.card_cardview_nowplaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie_id", String.valueOf(results.getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNowPlaying.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        private ImageView card_img_poster;
        private TextView card_txt_title, card_txt_overview, card_txt_releasedate;
        private CardView card_cardview_nowplaying;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            card_img_poster = itemView.findViewById(R.id.card_img_poster);
            card_txt_title = itemView.findViewById(R.id.card_txt_title);
            card_txt_overview = itemView.findViewById(R.id.card_txt_overview);
            card_txt_releasedate = itemView.findViewById(R.id.card_txt_releasedate);
            card_cardview_nowplaying = itemView.findViewById(R.id.card_cardview_nowplaying);
        }
    }
}
