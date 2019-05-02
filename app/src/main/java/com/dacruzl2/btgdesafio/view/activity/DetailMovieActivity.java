package com.dacruzl2.btgdesafio.view.activity;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.dacruzl2.btgdesafio.R;
import com.dacruzl2.btgdesafio.model.ViewModel.AppExecutors;
import com.dacruzl2.btgdesafio.model.ViewModel.FavoritosDatabase;
import com.dacruzl2.btgdesafio.model.ViewModel.FavoritosViewModel;
import com.dacruzl2.btgdesafio.model.pojos.Genre;
import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.presenter.impl.DetailMovieAPresenterImpl;
import com.dacruzl2.btgdesafio.presenter.inter.IDetailMovieAPresenter;
import com.dacruzl2.btgdesafio.view.fragment.FavoritosAdapter;
import com.dacruzl2.btgdesafio.view.inter.IDetailMovieAView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    boolean favorite;

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    private IDetailMovieAPresenter mIDetailMovieAPresenter;


    private List<Genre> genreList;
    Genre genre = new Genre();
    ArrayList<Integer> mGenreIds;

    private List<Movie> favList;
    Movie movie = new Movie();

    private FavoritosAdapter favAdapter;

    String palavra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIDetailMovieAPresenter = new DetailMovieAPresenterImpl(this);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        favoritosViewModel = ViewModelProviders.of(this).get(FavoritosViewModel.class);
        favoritosDatabase = FavoritosDatabase.getInstance(this);

        // favAdapter = new FavoritosAdapter(this, favList);
        favList = new ArrayList<>();
        genreList = new ArrayList<>();

        favoritosViewModel.getaAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                favList.clear();
                favList.addAll(movies);
/*
                for (int i = 0; i < movies.size(); i++) {
                    favList.add(i, movies.get(i));
                    Log.d("LISTFOR1", "titulo: "+ movies.get(i).getTitle());
                }*/
                // favAdapter.notifyDataSetChanged();

                for (Movie movie1 : favList) {
                    // recebe a palavra
                    movie = movie1;
                    Log.d("LISTFOR2", "titulo: " + movie.getTitle());
                    if (title.equals(movie.getTitle())) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_on));
                            }
                        });
                        //Assim que achar o titulo no banco de dados com o titulo que o usuario
                        //está abrindo na activity atual, ele parará o foreach e configurará o floatButton
                        // para que o icone de coração fique preenchido, senão achar o mesmo titulo ele deixará o floatButton
                        // com o icone de coração não preenchido;
                        break;
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_off));
                            }
                        });
                    }

                }
            }
        });


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
        genreList = it.getParcelableArrayListExtra("genreList");
        Log.d("GENRELIST", "EL: " + genreList.size());
        favorite = it.getBooleanExtra("isFavorite", false);


        List<Integer> genreList1 = new ArrayList<>();
        ArrayList[] a = new ArrayList[20];

        ArrayMap<String, Integer> listmovie = new ArrayMap<>();
        String[] name = new String[5];

        int array;

        for (int k = 0; k < genreList.size(); k++) {
            Log.d("GENRELISTFOR", "RETURN: " + genreList.get(k).getName());
            Log.d("GENRELISTFORID", "RETURN  " + genreList.get(k).getId());

            // String palavra = genreList.get(k).getName();
            //int id  = genreList.get(k).getId();

            for (int i = 0; i < mGenreIds.size(); i++) {

                //  Integer ids = mGenreIds.get(i);]
                if (genreList.get(k).getId() == mGenreIds.get(i)) {

                    a[i] = mGenreIds;

                    name[i] = genreList.get(k).getName();


                    tvGenerosResult.setText(genreList.get(k).getName());

                    tvGeneros.setText(genreList.get(k).getName());

                    Log.d("a[i]", "A[i]: " + a[i]);
                    Log.d("STRING NAME", "NAMES: " + name[i]);
                    Log.d("tvGeneroResult", "genreList.get(k).getName():" + genreList.get(k).getName());
                    // Log.d("mGenreIds FOR", "RETURN : " + mGenreIds.size());

                }
            }
        }


       /* for (Genre genre : genreList) {
            if (genreList.contains(genre.getId())) {
                genreList.add(genre);
            }
*/




      /*  for (Genre genre1 : genreList) {
            // recebe a palavra
            genre = genre1;


            Log.d("genre", "genreResult: "+ genre.getId());

            if ( genreList.contains(mGenreIds)){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvGenerosResult.setText(genreList.get().getName());
                    }
                });

            }
            break;
        }*/

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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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

                    editor.putString("favorite_on", title);
                    editor.apply();

                } else {
                    //Remove and display toast and change drawable
                    favoritosDatabase.getFavoritosDao().removeMovieFromFavorites(favMovie);
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
