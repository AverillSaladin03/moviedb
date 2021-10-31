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
import com.example.moviedb.adapter.TopRatedAdapter;
import com.example.moviedb.adapter.UpComingAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.helper.Loading;
import com.example.moviedb.model.TopRated;
import com.example.moviedb.model.UpComing;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopRatedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRatedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopRatedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopRatedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopRatedFragment newInstance(String param1, String param2) {
        TopRatedFragment fragment = new TopRatedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = new Loading(getActivity());
        loading.startLoading();
    }

    private RecyclerView fragTopRate_rv_topRateList;
    private MovieViewModel model;

    private int page;
    private boolean isLoading;
    private List<TopRated.Results> listResult;
    private TopRatedAdapter adapter;
    private Loading loading;

    private Observer<TopRated> showResults = new Observer<TopRated>() {
        @Override
        public void onChanged(TopRated topRated) {
            for (int i = 0; i < topRated.getResults().size(); i++){
                listResult.add(topRated.getResults().get(i));
            }
            adapter.notifyDataSetChanged();
            loading.loadDismiss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);

        page = 1;
        isLoading = false;

        fragTopRate_rv_topRateList = view.findViewById(R.id.fragTopRate_rv_topRateList);
        fragTopRate_rv_topRateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TopRatedAdapter(getActivity());
        listResult = new ArrayList<>();

        adapter.setTopRatedResults(listResult);
        fragTopRate_rv_topRateList.setAdapter(adapter);

        model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        model.getTopRatedMovies(page);
        model.getResultGetTopRatedMovies().observe(getActivity(), showResults);

        listener();

        return view;
    }

    private void listener() {

        ItemClickSupport.addTo(fragTopRate_rv_topRateList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movie_id", String.valueOf(listResult.get(position).getId()));
                Navigation.findNavController(getView()).
                        navigate(R.id.action_topRatedFragment3_to_movieDetailsFragment, bundle);
            }
        });

        fragTopRate_rv_topRateList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(!isLoading) {
                    isLoading = true;
                    getMoreData();
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
                model.getTopRatedMovies(page);
                model.getResultGetTopRatedMovies().observe(getActivity(), showResults);
                isLoading = false;
            }
        }, 1000);
    }
}