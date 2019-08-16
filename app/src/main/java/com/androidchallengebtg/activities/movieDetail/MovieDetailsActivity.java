package com.androidchallengebtg.activities.movieDetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidchallengebtg.R;
import com.androidchallengebtg.helpers.EventBus;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private MovieDetailController movieDetailController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if(getIntent().getExtras()!=null){
            String movie = getIntent().getStringExtra("movie");
            try {
                movieDetailController = new MovieDetailController(new JSONObject(movie));
                fillScreen();
                getMovieDetails();
                getMovieAccountState();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FloatingActionButton floatingActionButton = findViewById(R.id.floatButtonFav);
            floatingActionButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatButtonFav:{
                markAsFavorite();
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    metodos para interagir com a api.
     */
    private void markAsFavorite(){
        movieDetailController.markAsFavorite(new MovieDetailController.MovieChangeLister(){
            @Override
            public void onSuccess() {
                getMovieAccountState();
                EventBus.getInstance().emit("olá");
            }

            @Override
            public void onError(String message) {
                showToast(message);
            }
        });

    }

    private void getMovieDetails(){
        movieDetailController.getMovie(new MovieDetailController.MovieChangeLister() {
            @Override
            public void onSuccess() {
                fillScreen();
            }

            @Override
            public void onError(String message) {
                showToast(message);
            }
        });
    }

    private void getMovieAccountState(){
        movieDetailController.getMovieStatus(new MovieDetailController.MovieChangeLister() {
            @Override
            public void onSuccess() {
                fillStatus();
            }

            @Override
            public void onError(String message) {
                showToast(message);
            }
        });
    }

    /*
    Metodos que alteram a tela
     */
    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void fillStatus(){
        FloatingActionButton floatingActionButton = findViewById(R.id.floatButtonFav);
        if(movieDetailController.isFavorite()){
            floatingActionButton.setImageDrawable(this.getDrawable(R.drawable.heart_custom));
        }else{
            floatingActionButton.setImageDrawable(this.getDrawable(R.drawable.heart_outline_custom));
        }
    }

    /*
    Preenche a tela com os dados do filme.
     */
    private void fillScreen(){
        TextView tvTitle = findViewById(R.id.title);
        TextView tvOverview = findViewById(R.id.overview);
        TextView tvVoteAverage = findViewById(R.id.voteAverage);
        TextView tvGenres = findViewById(R.id.genres);
        ImageView ivBackdrop = findViewById(R.id.backdrop);

        Picasso.get().load(movieDetailController.getBackdropUrl()).into(ivBackdrop);
        tvTitle.setText(movieDetailController.getTitle());
        tvOverview.setText(movieDetailController.getOverview());
        tvVoteAverage.setText(movieDetailController.getVoteAverage());
        tvGenres.setText(movieDetailController.getGenres());
    }
}
