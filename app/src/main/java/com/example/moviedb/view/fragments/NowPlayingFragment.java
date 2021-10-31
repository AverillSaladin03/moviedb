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
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.helper.Loading;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.example.moviedb.helper.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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

    private RecyclerView fragnowplaying_rv_nowplaying;
    private MovieViewModel viewModel;
    private boolean isLoading;
    private int page;
    private List<NowPlaying.Results> listResult;
    private NowPlayingAdapter adapter;
    private Loading loading;

    private Observer<NowPlaying> showNowPlayingResult = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {

            for (int i = 0; i < nowPlaying.getResults().size(); i++){
                listResult.add(nowPlaying.getResults().get(i));
            }
            adapter.notifyDataSetChanged();
            loading.loadDismiss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        page = 1;
        isLoading = false;
        listResult = new ArrayList<>();

        fragnowplaying_rv_nowplaying = view.findViewById(R.id.fragnowplaying_rv_nowplaying);
        fragnowplaying_rv_nowplaying.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new NowPlayingAdapter(getActivity());
        adapter.setListNowPlaying(listResult);
        fragnowplaying_rv_nowplaying.setAdapter(adapter);

        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        viewModel.getNowPlaying(page);
        viewModel.getResultNowPlaying().observe(getActivity(), showNowPlayingResult);

        listener();

        return view;
    }

    public void listener(){
        //            ItemClickSupport.addTo(fragnowplaying_rv_nowplaying).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
//                    return false;
//                }
//            });

        ItemClickSupport.addTo(fragnowplaying_rv_nowplaying).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("movie_id", String.valueOf(listResult.get(position).getId()));
                Navigation.findNavController(getView()).
                        navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment, bundle);
            }
        });

        fragnowplaying_rv_nowplaying.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                viewModel.getNowPlaying(page);
                viewModel.getResultNowPlaying().observe(getActivity(), showNowPlayingResult);
                isLoading = false;
            }
        }, 1000);

    }
}