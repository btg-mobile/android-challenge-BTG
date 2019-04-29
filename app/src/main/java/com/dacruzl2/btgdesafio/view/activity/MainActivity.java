package com.dacruzl2.btgdesafio.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.dacruzl2.btgdesafio.R;
import com.dacruzl2.btgdesafio.model.ViewModel.FavoritosDatabase;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;
import com.dacruzl2.btgdesafio.presenter.impl.MainActivityPresenter;
import com.dacruzl2.btgdesafio.presenter.inter.IMainAPresenter;
import com.dacruzl2.btgdesafio.view.fragment.FavoritosFragment;
import com.dacruzl2.btgdesafio.view.fragment.FilmesFragment;
import com.dacruzl2.btgdesafio.view.inter.IMainActivityView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainActivityView {

    private IMainAPresenter mIMainAPresenter;

    @BindView(R.id.layout_MainActivity)
    ConstraintLayout layout_MainActivity;

    @BindView(R.id.searchViewFilmes)
    SearchView searchViewFilmes;

    @BindView(R.id.searchViewFavoritos)
    SearchView searchViewFavoritos;

    @BindView(R.id.rvSearchFilmes)
    RecyclerView rvSearchFilmes;

    @BindView(R.id.viewpager_smart)
    ViewPager viewPager;

    @BindView(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Movie> searchFilmesList;
    private SearchFilmesAdapter searchFilmesAdapter;

    FavoritosDatabase favoritosDatabase;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainAPresenter = new MainActivityPresenter(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolBarMain();

        searchFilmesList = new ArrayList<>();
        searchFilmesAdapter = new SearchFilmesAdapter(this, searchFilmesList);

        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvSearchFilmes.setHasFixedSize(true);
        rvSearchFilmes.setLayoutManager(layoutManager);
        rvSearchFilmes.setAdapter(searchFilmesAdapter);
        searchViewFilmes.setQueryHint(Html.fromHtml("<font color = #ffffff>"
                + getResources().getString(R.string.search_view_filmes_hint) + "</font>"));
       // searchViewFilmes.setQueryHint("Procure Filmes");
        searchViewFilmes.requestFocus();

        searchViewFavoritos.setVisibility(View.INVISIBLE);

        //TabLayout com os dois itens: Filmes e Favoritos
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Filmes", FilmesFragment.class)
                        .add("Favoritos", FavoritosFragment.class)
                        .create()
        );

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

        mIMainAPresenter.configViewPagerScrollAndClick(viewPager);
        mIMainAPresenter.searchMoviesWithQuery(searchViewFilmes, rvSearchFilmes,
                searchFilmesList, MainActivity.this, searchFilmesAdapter, layout_MainActivity);

    }

    @Override
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showSearchFilmes() {
        searchViewFilmes.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchFavoritos() {
        searchViewFavoritos.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchFilmes() {
        searchViewFilmes.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSearchFavoritos() {
        searchViewFavoritos.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideViewPager() {
        viewPager.setVisibility(View.GONE);
        viewPagerTab.setVisibility(View.GONE);
    }

    @Override
    public void showViewPager() {
        viewPager.setVisibility(View.VISIBLE);
        viewPagerTab.setVisibility(View.VISIBLE);
    }

    @Override
    public void addNewItemSearchToFeed(Root root) {
        searchFilmesList.addAll(root.getResults());
        searchFilmesAdapter.notifyItemInserted(rvSearchFilmes.getId());
    }

    @Override
    public void setupToolBarForSearch() {
        toolbar.setTitle("Procurando Filmes");
        setSupportActionBar(toolbar);
    }

    @Override
    public void setupToolBarMain() {
        toolbar.setTitle("Filmes");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIMainAPresenter.onAttachView(this);
        searchViewFilmes.setIconifiedByDefault(false);
        if (searchViewFavoritos.isIconified()) {
            searchViewFavoritos.setIconifiedByDefault(false);
            searchViewFavoritos.setQueryHint("Procurar por nome ou data");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIMainAPresenter.onDetachView();
    }
}