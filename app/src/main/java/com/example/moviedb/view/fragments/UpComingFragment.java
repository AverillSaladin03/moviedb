package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.adapter.UpComingAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.helper.Loading;
import com.example.moviedb.model.UpComing;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpComingFragment extends Fragment {

    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = new Loading(getActivity());
        loading.startLoading();
    }

    private RecyclerView fragupcoming_rv_upcoming;
    private MovieViewModel model;

    private boolean isLoading;
    private int page;
    private List<UpComing.Results> listResult;
    private UpComingAdapter adapter;

    private Loading loading;

    private Observer<UpComing> showResult = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {
            for (int i = 0; i < upComing.getResults().size(); i++){
                listResult.add(upComing.getResults().get(i));
            }
            adapter.notifyDataSetChanged();
            loading.loadDismiss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);

        page = 1;
        isLoading = false;
        listResult = new ArrayList<>();

        fragupcoming_rv_upcoming = view.findViewById(R.id.fragupcoming_rv_upcominglist);
        fragupcoming_rv_upcoming.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new UpComingAdapter(getActivity());
        adapter.setUpComingList(listResult);
        fragupcoming_rv_upcoming.setAdapter(adapter);

        model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        model.getUpComingMovies(page);
        model.getResultGetUpComingMovies().observe(getActivity(), showResult);

        listener();

        return view;
    }

    private void listener(){
        ItemClickSupport.addTo(fragupcoming_rv_upcoming).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movie_id", String.valueOf(listResult.get(position).getId()));
                Navigation.findNavController(getView()).
                        navigate(R.id.action_upComingFragment_to_movieDetailsFragment, bundle);
            }
        });

        fragupcoming_rv_upcoming.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listResult.size()-1){
                        isLoading = true;
                        getMoreData();
                    }
                }
            }
        });
    }

    private void getMoreData() {
        listResult.add(null);
        adapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listResult.remove(null);
                page++;
                model.getUpComingMovies(page);
                model.getResultGetUpComingMovies().observe(getActivity(), showResult);
                isLoading = false;
            }
        }, 1000);

    }
}