package com.example.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.view.activites.MovieDetailsActivity;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Variable(s)
    final int VIEW_TYPE_LOADING = 0;
    final int VIEW_TYPE_ITEM = 1;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent,
                    false);
            return new NowPlayingAdapter.CardViewViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent,
                    false);
            return new NowPlayingAdapter.LoadingHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NowPlayingAdapter.CardViewViewHolder) {
            setViewValues((NowPlayingAdapter.CardViewViewHolder) holder, position);
        }
    }

    private void setViewValues(CardViewViewHolder holder, int position) {
        final  NowPlaying.Results results = getListNowPlaying().get(position);
        String FULL_IMG_PATH = Const.IMG_PATH + results.getPoster_path();
        Glide.with(context)
                .load(FULL_IMG_PATH)
                .into(holder.card_img_poster);

        holder.card_txt_title.setText(results.getTitle());
        holder.card_txt_overview.setText(results.getOverview());
        holder.card_txt_releasedate.setText(results.getRelease_date());
//        holder.card_cardview_nowplaying.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Use INTENT
////                Intent intent = new Intent(context, MovieDetailsActivity.class);
////                intent.putExtra("movie_id", String.valueOf(results.getId()));
////                context.startActivity(intent);
//
//                //Use BUNDLE + ARGUMENT on NAVIGATION
//                Bundle bundle = new Bundle();
//                bundle.putString("movie_id", String.valueOf(results.getId()));
//                Navigation.findNavController(view).
//                        navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment, bundle);
//
//            }
//        });
    }

    @Override
    public int getItemViewType(int position) {
        return listNowPlaying.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
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

    private class LoadingHolder extends RecyclerView.ViewHolder{

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
