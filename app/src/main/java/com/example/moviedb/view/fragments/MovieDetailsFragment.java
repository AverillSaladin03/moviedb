package com.example.moviedb.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.GenreAdapter;
import com.example.moviedb.adapter.ProductionCompanyAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.helper.Loading;
import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.view.activites.MovieDetailsActivity;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private Loading loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        loading = new Loading(getActivity());
        loading.startLoading();
    }

    private TextView fragmovdetail_txt_title, fragmovdetail_txt_year, fragmovdetail_txt_score, fragmovdetail_txt_tagline
            , fragmovdetail_txt_orititle, fragmovdetail_txt_director, fragmovdetail_txt_releasedate, fragmovdetail_txt_overview, fragmovdetail_txt_popular;
    private ImageView fragmovdetail_img_background, fragmovdetail_img_poster;
    private RecyclerView fragmovdetail_rv_genre, fragmovdetail_rv_productioncomp;
    private RatingBar fragmovdetail_ratingbar_score;

    private String movie_id = "", title = "", releasedate = "", year = "", score = "", tagline = "", orititle = "", directorStr = "", overview = ""
            , POSTER_FULL_PATH = "", BACKGROUND_FULL_PATH = "", popularity = "", totalVote="", date = "", month = "";
    private MovieViewModel viewModel;
    private GenreAdapter genreAdapter;
    private ProductionCompanyAdapter productionCompanyAdapter;

    private Observer<Credits> showCreditsByIdResult = new Observer<Credits>() {
        @Override
        public void onChanged(Credits credits) {

            //Proses to get DIRECTOR from CREW
            List<Credits.Crew> crewList = credits.getCrew();
            ArrayList<String> directors = new ArrayList<>();
            for(int i = 0; i < crewList.size() ; i++){
                if(credits.getCrew().get(i).getJob().equalsIgnoreCase("director")) {
                    directors.add(credits.getCrew().get(i).getName());
                }
            }
            for (int j = 0; j < directors.size(); j++){
                if(directors.size()-1 == j){
                    directorStr += directors.get(j);
                }else{
                    directorStr += directors.get(j) + ", ";
                }
            }
            //Assign to XML
            fragmovdetail_txt_director.setText(directorStr);
        }
    };

    private Observer<Movies> showMovieByIdResult = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            //Proses to get YEAR from RELEASE DATE
            releasedate = movies.getRelease_date();
            year = releasedate.substring(0, 4);
            date = releasedate.substring(8,10);
            if (releasedate.substring(5,7).equalsIgnoreCase("01")){
                month = "January";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("02")){
                month = "February";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("03")){
                month = "March";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("04")){
                month = "April";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("05")){
                month = "May";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("06")){
                month = "June";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("07")){
                month = "July";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("08")){
                month = "August";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("09")){
                month = "September";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("10")){
                month = "October";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("11")){
                month = "November";
            }else if (releasedate.substring(5,7).equalsIgnoreCase("12")){
                month = "December";
            }

            //Assign to Variables
            title = movies.getTitle();
            score = String.valueOf(movies.getVote_average());
            float score5 = Float.valueOf(score)/2;
            tagline = movies.getTagline();
            orititle = movies.getOriginal_title();
            overview = movies.getOverview();
            POSTER_FULL_PATH = Const.IMG_PATH+movies.getPoster_path().toString();
            BACKGROUND_FULL_PATH = Const.IMG_PATH+movies.getBackdrop_path();
            totalVote = String.valueOf(movies.getVote_count());
            popularity = String.valueOf(movies.getPopularity());

            // Set to XML
            fragmovdetail_txt_title.setText(title);
            if (tagline.isEmpty()){
                fragmovdetail_txt_tagline.setText("-No Tagline-");
            }else{
                fragmovdetail_txt_tagline.setText(tagline);
            }
            fragmovdetail_txt_year.setText(year);
            fragmovdetail_txt_popular.setText("Popularity : " + popularity );

            fragmovdetail_txt_score.setText(score + " ("+totalVote+")");
            fragmovdetail_txt_orititle.setText(orititle);
            fragmovdetail_txt_overview.setText(overview);
            fragmovdetail_txt_releasedate.setText(date + " " + month + " " + year);
            fragmovdetail_ratingbar_score.setRating(score5);

            Glide.with(getActivity()).load(POSTER_FULL_PATH).into(fragmovdetail_img_poster);
            Glide.with(getActivity()).load(BACKGROUND_FULL_PATH).into(fragmovdetail_img_background);

            fragmovdetail_rv_genre.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false));
            genreAdapter.setGenresList(movies.getGenres());
            fragmovdetail_rv_genre.setAdapter(genreAdapter);

            fragmovdetail_rv_productioncomp.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false));
            productionCompanyAdapter.setProductionCompaniesList(movies.getProduction_companies());
            fragmovdetail_rv_productioncomp.setAdapter(productionCompanyAdapter);

            ItemClickSupport.addTo(fragmovdetail_rv_productioncomp).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    String name = movies.getProduction_companies().get(position).getName();
                    String originCountry = movies.getProduction_companies().get(position).getOrigin_country();
                    Snackbar snackbar = Snackbar.make(v, name + ", " + originCountry, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(getResources().getColor(R.color.dark_grey));
                    snackbar.setTextColor(getResources().getColor(R.color.gold));
                    snackbar.setAnchorView(R.id.main_botnav_menu);
                    snackbar.show();
                }
            });

            loading.loadDismiss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        initView(view);

        movie_id = getArguments().getString("movie_id");
        viewModel.getCreditsById(movie_id);
        viewModel.getResultGetCreditsById().observe(getActivity(), showCreditsByIdResult);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(getActivity(), showMovieByIdResult);

        return view;
    }

    private void initView(View view) {
        //Others
        genreAdapter = new GenreAdapter(getActivity());
        productionCompanyAdapter = new ProductionCompanyAdapter(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);

        //Text
        fragmovdetail_txt_title = view.findViewById(R.id.fragmovdetail_txt_title);
        fragmovdetail_txt_year = view.findViewById(R.id.fragmovdetail_txt_year);
        fragmovdetail_txt_score = view.findViewById(R.id.fragmovdetail_txt_score);
        fragmovdetail_txt_tagline = view.findViewById(R.id.fragmovdetail_txt_tagline);
        fragmovdetail_txt_orititle = view.findViewById(R.id.fragmovdetail_txt_orititle);
        fragmovdetail_txt_director = view.findViewById(R.id.fragmovdetail_txt_director);
        fragmovdetail_txt_releasedate = view.findViewById(R.id.fragmovdetail_txt_releasedate);
        fragmovdetail_txt_overview = view.findViewById(R.id.fragmovdetail_txt_overview);
        fragmovdetail_txt_popular = view.findViewById(R.id.fragmovdetail_txt_popular);

        //Recycler View
        fragmovdetail_rv_genre = view.findViewById(R.id.fragmovdetail_rv_genre);
        fragmovdetail_rv_productioncomp = view.findViewById(R.id.fragmovdetail_rv_productioncompany);

        //Rating Bar
        fragmovdetail_ratingbar_score = view.findViewById(R.id.fragmovdetail_ratingbar_score);

        //Image
        fragmovdetail_img_background = view.findViewById(R.id.fragmovdetail_img_background);
        fragmovdetail_img_poster = view.findViewById(R.id.fragmovdetails_img_poster);
    }

}