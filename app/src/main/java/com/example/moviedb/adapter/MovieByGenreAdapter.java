package com.example.moviedb.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.TopRated;
import com.example.moviedb.view.activites.MovieDetailsActivity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MovieByGenreAdapter extends RecyclerView.Adapter<MovieByGenreAdapter.movieCardViewViewHolder> {

    //Sebenarnya mau nampilin semua Movie, jadinya pakai nama Movie By Genre, tp tidak ketemu caranya, jadi basis datanya
    //Menggunakan API Top Rated Movies

    private Context context;
    private List<TopRated.Results> topRatedResults, showList = new List<TopRated.Results>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<TopRated.Results> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(TopRated.Results results) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends TopRated.Results> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends TopRated.Results> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public TopRated.Results get(int i) {
            return null;
        }

        @Override
        public TopRated.Results set(int i, TopRated.Results results) {
            return null;
        }

        @Override
        public void add(int i, TopRated.Results results) {

        }

        @Override
        public TopRated.Results remove(int i) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<TopRated.Results> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<TopRated.Results> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<TopRated.Results> subList(int i, int i1) {
            return null;
        }
    };
    private int id_genreGiven;
    private boolean genreStatus;

    //GetArrayList
    private List<TopRated.Results> getListGenre(){
        return topRatedResults;
    }
    //SetArrayList
    public void setMovieByGenresList(List<TopRated.Results> topRatedResults){
        this.topRatedResults = topRatedResults;
    }
    //Constructor
    public MovieByGenreAdapter (Context context, int id_genreGiven){
        this.id_genreGiven = id_genreGiven;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieByGenreAdapter.movieCardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie_by_genre, parent,
                false);
        return new MovieByGenreAdapter.movieCardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieByGenreAdapter.movieCardViewViewHolder holder, int position) {
        final TopRated.Results result = topRatedResults.get(position);


        for(int i = 0; i < result.getGenre_ids().size(); i++){
            if(result.getGenre_ids().get(i) == id_genreGiven) {
                showList.add(result);
                genreStatus = true;
                break;
            }else{
                genreStatus = false;
            }
        }
//
//        if(genreStatus == true){
            final TopRated.Results list = showList.get(position);
            String FULL_IMG_PATH = Const.IMG_PATH + list.getPoster_path();
            Glide.with(context)
                    .load(FULL_IMG_PATH)
                    .into(holder.card_img_poster);

            holder.card_txt_title.setText(list.getTitle());
            holder.card_txt_overview.setText(list.getOverview());
            holder.card_txt_releasedate.setText(list.getRelease_date());
            holder.card_cardview_moviebygenre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra("movie_id", String.valueOf(list.getId()));
                    context.startActivity(intent);
                }
            });
//        }



    }

    @Override
    public int getItemCount() {
        return topRatedResults.size();
    }

    public class movieCardViewViewHolder extends RecyclerView.ViewHolder {
        private ImageView card_img_poster;
        private TextView card_txt_title, card_txt_overview, card_txt_releasedate;
        private CardView card_cardview_moviebygenre;

        public movieCardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            card_img_poster = itemView.findViewById(R.id.card_img_poster);
            card_txt_title = itemView.findViewById(R.id.card_txt_title);
            card_txt_overview = itemView.findViewById(R.id.card_txt_overview);
            card_txt_releasedate = itemView.findViewById(R.id.card_txt_releasedate);
            card_cardview_moviebygenre = itemView.findViewById(R.id.card_cardview_moviebygenre);
        }
    }
}