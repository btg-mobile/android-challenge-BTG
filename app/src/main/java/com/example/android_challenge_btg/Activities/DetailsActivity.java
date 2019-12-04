package com.example.android_challenge_btg.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_challenge_btg.R;
import com.example.android_challenge_btg.adapters.GenresAdapter;
import com.example.android_challenge_btg.models.Movie;
import com.example.android_challenge_btg.services.DataBaseService;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.detail_movie_title)
    TextView detailMovieTitle;
    @BindView(R.id.datail_movie_poster)
    ImageView detailMoviePoster;
    @BindView(R.id.switch_fav)
    Switch switchFav;
    @BindView(R.id.genres_rv)
    RecyclerView genresRV;
    @BindView(R.id.rate_movie)
    TextView rateMovie;
    @BindView(R.id.detail_movie_overview)
    TextView detailMovieOverview;

    private Movie movie;
    private final String imageUrl = "http://image.tmdb.org/t/p/w300/";
    private Context context;
    RuntimeExceptionDao<Movie, Long> movieDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        this.movie = (Movie) getIntent().getSerializableExtra("movie");

        init();
    }

    private void init() {
        context = getApplicationContext();
         movieDao = DataBaseService.getInstance(context).getRuntimeExceptionDao(Movie.class);

        this.detailMovieTitle.setText(this.movie.getTitle());
        Glide.with(context).load(imageUrl + this.movie.getPosterPath())
                .into(this.detailMoviePoster);
        this.switchFav.setChecked(isAFavMovie());
        this.rateMovie.setText(this.movie.getVoteAverage().toString());
        this.detailMovieOverview.setText(this.movie.getOverview());

        this.genresRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        GenresAdapter genresAdapter = new GenresAdapter(movie.getGenres(DataBaseService.getInstance(context)));
        this.genresRV.setAdapter(genresAdapter);
    }

    private boolean isAFavMovie() {
        return movieDao.idExists(movie.getId());
    }

    @OnClick(R.id.switch_fav)
    public void onChangedSwitch(View view){
        try {
            if(this.switchFav.isChecked()) {
                this.movie.getGenreIdsStr();
                this.movieDao.create(this.movie);
            } else {
                this.movieDao.deleteById(this.movie.getId());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
