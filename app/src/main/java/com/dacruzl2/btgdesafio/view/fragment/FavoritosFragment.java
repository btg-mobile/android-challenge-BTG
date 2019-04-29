package com.dacruzl2.btgdesafio.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.dacruzl2.btgdesafio.R;
import com.dacruzl2.btgdesafio.model.ViewModel.FavoritosViewModel;
import com.dacruzl2.btgdesafio.model.ViewModel.FavoritosDatabase;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.presenter.impl.FavoritosPresenter;
import com.dacruzl2.btgdesafio.presenter.inter.IFavoritosFPresenter;
import com.dacruzl2.btgdesafio.view.inter.IFavoritosView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritosFragment extends Fragment implements IFavoritosView {

    private IFavoritosFPresenter mIFavoritosFPresenter;
    FavoritosViewModel favoritosViewModel;
    FavoritosDatabase favoritosDatabase;

    @BindView(R.id.rvFavoritosFragment)
    RecyclerView rvFavoritosFragment;

    private FavoritosAdapter favAdapter;
    private List<Movie> favList;

    //Injetando dependência do presenter
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIFavoritosFPresenter = new FavoritosPresenter(this);
        favoritosViewModel = ViewModelProviders.of(this).get(FavoritosViewModel.class);
        favList = new ArrayList<>();
        favoritosDatabase = FavoritosDatabase.getInstance(getActivity());

    }

    public FavoritosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        ButterKnife.bind(this, view);

        favAdapter = new FavoritosAdapter(getActivity(), favList);

        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvFavoritosFragment.setItemAnimator(new DefaultItemAnimator());
        rvFavoritosFragment.setHasFixedSize(true);
        rvFavoritosFragment.setLayoutManager(layoutManager);
        rvFavoritosFragment.setAdapter(favAdapter);

        favoritosViewModel.getaAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                favList.clear();
                favList.addAll(movies);
                favAdapter.notifyDataSetChanged();
            }
        });





        return view;
    }
}
