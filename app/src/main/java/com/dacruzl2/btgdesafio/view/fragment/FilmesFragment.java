package com.dacruzl2.btgdesafio.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dacruzl2.btgdesafio.R;
import com.dacruzl2.btgdesafio.RecyclerItemClickListener;
import com.dacruzl2.btgdesafio.model.pojos.Genre;
import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;
import com.dacruzl2.btgdesafio.presenter.impl.FilmesPresenter;
import com.dacruzl2.btgdesafio.presenter.inter.IFilmesFPresenter;
import com.dacruzl2.btgdesafio.view.activity.DetailMovieActivity;
import com.dacruzl2.btgdesafio.view.inter.IFilmesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FilmesFragment extends Fragment implements IFilmesView {

    private IFilmesFPresenter mIFilmesFPresenter;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout refresh;

    @BindView(R.id.rvFilmesFragment)
    RecyclerView rvFilmesFragment;

    @BindView(R.id.progress_filmes_fragment)
    ProgressBar progress_filmes_fragment;

    private FilmesAdapter movieAdapter;
    private List<Movie> movieList;
    private List<Genre> genresList;

    //Injetando dependência do presenter
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIFilmesFPresenter = new FilmesPresenter(this);
        movieList = new ArrayList<>();
        genresList = new ArrayList<>();
    }

    public FilmesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filmes, container, false);
        ButterKnife.bind(this, view);
        movieAdapter = new FilmesAdapter(getActivity(), movieList, genresList);


        rvFilmesFragment.setItemAnimator(new DefaultItemAnimator());
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvFilmesFragment.setHasFixedSize(true);
        rvFilmesFragment.setLayoutManager(layoutManager);
        rvFilmesFragment.setAdapter(movieAdapter);
        rvFilmesFragment.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                rvFilmesFragment, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Movie movie = movieList.get(position);

                RootGenres rootGenres = new RootGenres(genresList);


                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("overview", movie.getOverview());
                intent.putExtra("poster", movie.getPosterPath());
                intent.putExtra("backdrop", movie.getBackdropPath());
                intent.putExtra("vote_average",movie.getVoteAverage());
                intent.putExtra("releaseDate", movie.getReleaseDate());
                intent.putExtra("movieID", movie.getId());
                intent.putIntegerArrayListExtra("generoID", (ArrayList<Integer>) movie.getGenreIds());
                intent.putParcelableArrayListExtra("genreList", (ArrayList<? extends Parcelable>) rootGenres.getGenres());
                //intent.putExtra("genreID", genre.getId());
                // intent.putParcelableArrayListExtra("listmovies", (ArrayList<? extends Parcelable>) movieList);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        mIFilmesFPresenter.configEndlessScroll(rvFilmesFragment, getActivity(), layoutManager);

        refresh.setColorSchemeResources(android.R.color.holo_orange_dark);
        refresh.setOnRefreshListener(() -> mIFilmesFPresenter.refreshLayout(movieList, getActivity()));

        return view;
    }

    @Override
    public void addNewItemToFeed(Root root) {
        movieList.addAll(root.getResults());
        movieAdapter.notifyItemInserted(rvFilmesFragment.getId());
    }

    @Override
    public void addIdMovieForGetGenres(RootGenres rootGenres) {
        genresList.addAll(rootGenres.getGenres());
    }

    @Override
    public void hideRefresh() {
        refresh.setRefreshing(false);
    }

    @Override
    public void showRefresh() {
        refresh.setRefreshing(true);
    }

    @Override
    public void showProgressBar() {
        progress_filmes_fragment.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progress_filmes_fragment.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mIFilmesFPresenter.onAttachView(this);
        if (movieList.size() == 0) {
            mIFilmesFPresenter.retrievePopularMovies(getActivity());
            mIFilmesFPresenter.retrieveGenre(getActivity());
            rvFilmesFragment.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onStop() {
        mIFilmesFPresenter.onDetachView();
        super.onStop();
    }
}
