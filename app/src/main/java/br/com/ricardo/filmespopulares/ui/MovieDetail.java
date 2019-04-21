package br.com.ricardo.filmespopulares.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.ricardo.filmespopulares.R;
import br.com.ricardo.filmespopulares.data.network.model.Film;
import br.com.ricardo.filmespopulares.utils.ErrorMessage;
import br.com.ricardo.filmespopulares.utils.KeepData;

public class MovieDetail extends AppCompatActivity {

    private static final String PREFS_NAME = "Preferences";
    public static final String EXTRA_FILM = "EXTRA_FILM";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private final String backDrop = "w780";
    private final String thumb = "w154";

    private Toolbar toolbarMovieDetail;
    private ImageView imageMovieDetail;
    private ImageView imageMovieThumb;
    private TextView textMovideDetailOriginalName;
    private TextView textMovideDetailGenre;
    private TextView textMovideDetailRate;
    private CheckBox checkMovieDetailFavorite;
    private TextView textMovideDetailOverview;

    private Film film;
    private KeepData prefs;
    private ErrorMessage errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        prefs = new KeepData(getSharedPreferences(PREFS_NAME, MODE_PRIVATE));
        errorMessage = new ErrorMessage();

        toolbarMovieDetail = (Toolbar) findViewById(R.id.toolbar_movie_detail);
        setSupportActionBar(toolbarMovieDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        imageMovieDetail = (ImageView) findViewById(R.id.image_movie_detail);
        imageMovieThumb = (ImageView) findViewById(R.id.image_movie_detail_thumb);
        textMovideDetailOriginalName = (TextView) findViewById(R.id.text_movie_detail_original_name);
        textMovideDetailGenre = (TextView) findViewById(R.id.text_movie_detail_genre);
        textMovideDetailRate = (TextView) findViewById(R.id.text_movie_detail_rate);
        checkMovieDetailFavorite = (CheckBox) findViewById(R.id.check_movie_detail_favorite);
        textMovideDetailOverview = (TextView) findViewById(R.id.text_movie_detail_overview);

        film = (Film) getIntent().getSerializableExtra(EXTRA_FILM);

        if(film != null){

            getSupportActionBar().setTitle(film.getTitle());

            Picasso.with(this)
                    .load(IMAGE_BASE_URL + backDrop + film.getBackdropPath())
                    .into(this.imageMovieDetail);

            Picasso.with(this)
                    .load(IMAGE_BASE_URL + thumb + film.getPosterPath())
                    .into(this.imageMovieThumb);

            ArrayList<Integer> arrayGenre = film.getGenres();

            textMovideDetailOriginalName.setText(film.getOriginalTitle());
            textMovideDetailGenre.setText(getGenreText(arrayGenre));
            textMovideDetailRate.setText(film.getRate());
            textMovideDetailOverview.setText(film.getOverview());
            checkMovieDetailFavorite.setChecked(prefs.recoverFlagFavorite(String.valueOf(film.getMovieId())));

            checkMovieDetailFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(checkMovieDetailFavorite.isChecked()){
                        prefs.saveFlagFavoriteMovie(String.valueOf(film.getMovieId()), true);
                    } else {
                        prefs.saveFlagFavoriteMovie(String.valueOf(film.getMovieId()), false);
                    }
                }
            });

        }else {
            errorMessage.showError(this, getString(R.string.error_movie_detail));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    public String getGenreText(ArrayList<Integer> numbers){

        StringBuilder unabbreviated = new StringBuilder();

        for(int i = 0; i < numbers.size(); i++){

            switch (numbers.get(i)){

                case 28:
                    unabbreviated.append(getString(R.string.genre_action));
                    unabbreviated.append(", ");
                    break;

                case 12:
                    unabbreviated.append(getString(R.string.genre_adventure));
                    unabbreviated.append(", ");
                    break;

                case 16:
                    unabbreviated.append(getString(R.string.genre_animation));
                    unabbreviated.append(", ");
                    break;

                case 35:
                    unabbreviated.append(getString(R.string.genre_comedy));
                    unabbreviated.append(", ");
                    break;

                case 80:
                    unabbreviated.append(getString(R.string.genre_crime));
                    unabbreviated.append(", ");
                    break;

                case 99:
                    unabbreviated.append(getString(R.string.genre_documentary));
                    unabbreviated.append(", ");
                    break;

                case 18:
                    unabbreviated.append(getString(R.string.genre_drama));
                    unabbreviated.append(", ");
                    break;

                case 10751:
                    unabbreviated.append(getString(R.string.genre_family));
                    unabbreviated.append(", ");
                    break;

                case 14:
                    unabbreviated.append(getString(R.string.genre_fantasy));
                    unabbreviated.append(", ");
                    break;

                case 36:
                    unabbreviated.append(getString(R.string.genre_history));
                    unabbreviated.append(", ");
                    break;

                case 27:
                    unabbreviated.append(getString(R.string.genre_horror));
                    unabbreviated.append(", ");
                    break;

                case 10402:
                    unabbreviated.append(getString(R.string.genre_music));
                    unabbreviated.append(", ");
                    break;

                case 9648:
                    unabbreviated.append(getString(R.string.genre_mystery));
                    unabbreviated.append(", ");
                    break;

                case 10749:
                    unabbreviated.append(getString(R.string.genre_romance));
                    unabbreviated.append(", ");
                    break;

                case 878:
                    unabbreviated.append(getString(R.string.genre_science_fiction));
                    unabbreviated.append(", ");
                    break;

                case 10770:
                    unabbreviated.append(getString(R.string.genre_tv_movie));
                    unabbreviated.append(", ");
                    break;

                case 53:
                    unabbreviated.append(getString(R.string.genre_thriller));
                    unabbreviated.append(", ");
                    break;

                case 10752:
                    unabbreviated.append(getString(R.string.genre_war));
                    unabbreviated.append(", ");
                    break;

                case 37:
                    unabbreviated.append(getString(R.string.genre_western));
                    unabbreviated.append(", ");
                    break;
            }
        }

        String modified = unabbreviated.substring(0, unabbreviated.length() -2);

        return modified;
    }
}
