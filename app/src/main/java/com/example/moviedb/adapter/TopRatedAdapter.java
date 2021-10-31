package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.TopRated;
import com.example.moviedb.model.UpComing;

import java.util.List;

public class TopRatedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int VIEW_TYPE_LOADING = 0;
    final int VIEW_TYPE_ITEM = 1;
    private Context context;
    private List<TopRated.Results> topRatedResults;

    public TopRatedAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public List<TopRated.Results> getTopRatedResults() {
        return topRatedResults;
    }

    public void setTopRatedResults(List<TopRated.Results> topRatedResults) {
        this.topRatedResults = topRatedResults;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent,
                    false);
            return new TopRatedAdapter.CardViewViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent,
                    false);
            return new TopRatedAdapter.LoadingHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopRatedAdapter.CardViewViewHolder) {
            setViewValues((TopRatedAdapter.CardViewViewHolder) holder, position);
        }
    }

    private void setViewValues(CardViewViewHolder holder, int position) {
        final  TopRated.Results results = getTopRatedResults().get(position);
        String FULL_IMG_PATH = Const.IMG_PATH + results.getPoster_path();
        Glide.with(context)
                .load(FULL_IMG_PATH)
                .into(holder.card_img_poster);

        holder.card_txt_title.setText(results.getTitle());
        holder.card_txt_overview.setText(results.getOverview());
        holder.card_txt_releasedate.setText(results.getRelease_date());
    }

    @Override
    public int getItemViewType(int position) {
        return topRatedResults.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return getTopRatedResults().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        private ImageView card_img_poster;
        private TextView card_txt_title, card_txt_overview, card_txt_releasedate;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            card_img_poster = itemView.findViewById(R.id.card_img_poster);
            card_txt_title = itemView.findViewById(R.id.card_txt_title);
            card_txt_overview = itemView.findViewById(R.id.card_txt_overview);
            card_txt_releasedate = itemView.findViewById(R.id.card_txt_releasedate);
        }
    }
    private class LoadingHolder extends RecyclerView.ViewHolder{

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
