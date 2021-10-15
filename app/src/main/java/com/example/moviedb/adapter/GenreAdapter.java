package com.example.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.model.Movies;
import com.example.moviedb.view.MovieByGenreActivity;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.genreCardViewHolder> {

    private Context context;
    private List<Movies.Genres> genresList;

    //GetArrayList
    private List<Movies.Genres> getListGenre(){
        return genresList;
    }
    //SetArrayList
    public void setGenresList(List<Movies.Genres> genresList){
        this.genresList = genresList;
    }
    //Constructor
    public GenreAdapter (Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public GenreAdapter.genreCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_genre, parent,
                false);
        return new GenreAdapter.genreCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.genreCardViewHolder holder, int position) {
        final Movies.Genres genresResult = getListGenre().get(position);
        String genre = genresResult.getName();
        holder.card_txt_genre.setText(genre);
        holder.card_cv_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Masuk Page Movie By Genre
                Intent intent = new Intent(context, MovieByGenreActivity.class);
                intent.putExtra("genre", genre );
                intent.putExtra("genre_id", genresResult.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return genresList.size();
    }

    public class genreCardViewHolder extends RecyclerView.ViewHolder {
        private TextView card_txt_genre;
        private CardView card_cv_genre;

        public genreCardViewHolder(@NonNull View itemView) {
            super(itemView);
            card_txt_genre = itemView.findViewById(R.id.card_txt_genre);
            card_cv_genre = itemView.findViewById(R.id.card_cv_genre);
        }
    }
}
