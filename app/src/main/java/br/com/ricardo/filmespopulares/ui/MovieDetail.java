package br.com.ricardo.filmespopulares.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.ricardo.filmespopulares.R;
import br.com.ricardo.filmespopulares.data.network.model.Film;
import br.com.ricardo.filmespopulares.data.network.response.PopularResponseFilm;

public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_FILM = "EXTRA_FILM";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private final String backDrop = "w780";
    private final String thumb = "w154";

    private ProgressBar progressBarMovieDetail;
    private LinearLayout linearContainerMovieDetail;
    private TextView textMovieDetailTitle;
    private ImageView imageMovieDetail;
    private ImageView imageMovieThumb;
    private TextView textMovideDetailOriginalName;
    private TextView textMovideDetailLanguage;
    private TextView textMovideDetailRate;
    private TextView textMovideDetailOverview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        progressBarMovieDetail = (ProgressBar) findViewById(R.id.progressBar_movie_detail);
        linearContainerMovieDetail = (LinearLayout) findViewById(R.id.linear_container_movie_detail);
        textMovieDetailTitle = (TextView) findViewById(R.id.text_movie_detail_title);
        imageMovieDetail = (ImageView) findViewById(R.id.image_movie_detail);
        imageMovieThumb = (ImageView) findViewById(R.id.image_movie_detail_thumb);
        textMovideDetailOriginalName = (TextView) findViewById(R.id.text_movie_detail_original_name);
        textMovideDetailLanguage = (TextView) findViewById(R.id.text_movie_detail_language);
        textMovideDetailRate = (TextView) findViewById(R.id.text_movie_detail_rate);
        textMovideDetailOverview = (TextView) findViewById(R.id.text_movie_detail_overview);

        linearContainerMovieDetail.setVisibility(View.GONE);
        progressBarMovieDetail.setVisibility(View.VISIBLE);


        Film film = (Film) getIntent().getSerializableExtra(EXTRA_FILM);

        if(film != null){

            linearContainerMovieDetail.setVisibility(View.VISIBLE);
            progressBarMovieDetail.setVisibility(View.GONE);

            textMovieDetailTitle.setText(film.getTitle());

            Picasso.with(this)
                    .load(IMAGE_BASE_URL + backDrop + film.getBackdropPath())
                    .into(this.imageMovieDetail);

            Picasso.with(this)
                    .load(IMAGE_BASE_URL + thumb + film.getPosterPath())
                    .into(this.imageMovieThumb);

            textMovideDetailOriginalName.setText(film.getOriginalTitle());
            textMovideDetailLanguage.setText(film.getLanguage());
            textMovideDetailRate.setText(film.getRate());
            textMovideDetailOverview.setText(film.getOverview());

        }else {
            Toast.makeText(this, getString(R.string.error_movie_detail), Toast.LENGTH_SHORT).show();
        }
    }
}
