package com.dacruzl2.btgdesafio.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.dacruzl2.btgdesafio.R;
import com.dacruzl2.btgdesafio.model.ViewModel.AppExecutors;
import com.dacruzl2.btgdesafio.model.ViewModel.FavoritosDatabase;
import com.dacruzl2.btgdesafio.model.ViewModel.FavoritosViewModel;
import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.presenter.impl.DetailMovieAPresenterImpl;
import com.dacruzl2.btgdesafio.presenter.inter.IDetailMovieAPresenter;
import com.dacruzl2.btgdesafio.view.inter.IDetailMovieAView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yanzhikai.textpath.TextPathView;

public class DetailMovieActivity extends AppCompatActivity implements IDetailMovieAView {

    @BindView(R.id.ivDetailPoster)
    ImageView ivDetailPoster;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.tvTitleDetail)
    TextView tvTitleDetail;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.tvNota)
    TextPathView tvNota;

    @BindView(R.id.tvNotaResult)
    TextPathView tvNotaResult;

    @BindView(R.id.tvGeneros)
    TextPathView tvGeneros;

    @BindView(R.id.tvGenerosResult)
    TextPathView tvGenerosResult;

    @BindView(R.id.tvSinopse)
    TextPathView tvSinopse;

    FavoritosDatabase favoritosDatabase;
    FavoritosViewModel favoritosViewModel;

    private StyleableToast.Builder mToast;

    String detailPoster, title, overview, backdrop, releaseDate, genero;
    float vote_average;
    int movieID;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";
    ArrayList<Integer> mGenreIds;

    ArrayList<RootGenres> rootGenres;
    private IDetailMovieAPresenter mIDetailMovieAPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIDetailMovieAPresenter = new DetailMovieAPresenterImpl(this);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        favoritosViewModel = ViewModelProviders.of(this).get(FavoritosViewModel.class);
        favoritosDatabase = FavoritosDatabase.getInstance(this);

        floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_off));

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.move_image2);
        anim.setRepeatMode(Animation.INFINITE);
        ivDetailPoster.setAnimation(anim);

        tvNota.startAnimation(0, 1);
        tvNotaResult.startAnimation(0, 1);
        tvGeneros.startAnimation(0, 1);
        tvGenerosResult.startAnimation(0, 1);
        tvSinopse.startAnimation(0, 1);

        //Recuperando dados
        Intent it = getIntent();
        detailPoster = it.getStringExtra("poster");
        title = it.getStringExtra("title");
        overview = it.getStringExtra("overview");
        backdrop = it.getStringExtra("backdrop");
        vote_average = it.getFloatExtra("vote_average", 0);
        releaseDate = it.getStringExtra("releaseDate");
        movieID = it.getIntExtra("movieID", 0);
        mGenreIds = it.getIntegerArrayListExtra("generoID");
        genero = it.getStringExtra("genreName");
        rootGenres = it.getParcelableArrayListExtra("genreList");

        if (rootGenres.contains(mGenreIds)) {
            rootGenres.get(0).getGenres().size();
            tvGenerosResult.setText(rootGenres.get(0).getGenres().get(0).getName());
        }

        //Settando para as views
        tvTitleDetail.setText(title);
        tvOverview.setText(overview);
        tvNotaResult.setText(String.valueOf(vote_average));

        //tvGenerosResult.setText(rootGenres.get(0).getGenres().get(0).getName());


        Glide.with(this)
                .load("https://image.tmdb.org/t/p/original" + detailPoster)
                .into(ivDetailPoster);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adicionando ao DB
                addToFavouritesDatabaseOperations();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if (preferences.contains("favorite_on") || preferences.contains("favorite_off")) {

            int movieId = preferences.getInt("favorite_on",0 );

            int movieIdOff = preferences.getInt("favorite_off", 0);

            if(movieID == movieId){
                floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_on));
            }else if (movieID != movieIdOff) {
                floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_off));
            }
        }

    }

    @Override
    public void addIdMovieForGetGenres(RootGenres rootGenres) {

    }

    public void addToFavouritesDatabaseOperations() {

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        SharedPreferences.Editor editor = preferences.edit();

        Movie favMovie = new Movie(movieID, title, overview, releaseDate,
                detailPoster, backdrop, vote_average, true, mGenreIds);
        //Executor Background Thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //To Avoid Unique Constraint Error
                //If it doesnt exist in the database add it
                if (favoritosDatabase.getFavoritosDao().loadMovie(title) == null) {
                    favoritosDatabase.getFavoritosDao().saveMovieToFavorites(favMovie);
                    editor.putInt("favorite_on", movieID);
                    editor.apply();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mToast = new StyleableToast.Builder(DetailMovieActivity.this);
                            mToast.text(getString(R.string.label_added_favs))
                                    .length(Toast.LENGTH_SHORT)
                                    .textColor(getResources().getColor(R.color.default_background_color))
                                    .backgroundColor(getResources().getColor(R.color.design_default_color_primary))
                                    .show();
                            floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_on));

                        }
                    });


                } else {
                    //Remove and display toast and change drawable
                    favoritosDatabase.getFavoritosDao().removeMovieFromFavorites(favMovie);

                    editor.putInt("favorite_off", 0);
                    editor.commit();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mToast = new StyleableToast.Builder(DetailMovieActivity.this);
                            mToast.text(getString(R.string.label_removed_favs))
                                    .length(Toast.LENGTH_SHORT)
                                    .textColor(getResources().getColor(R.color.white))
                                    .backgroundColor(getResources().getColor(R.color.colorAccent))
                                    .show();
                            floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_off));

                        }
                    });
                }
            }
        });
    }
}
