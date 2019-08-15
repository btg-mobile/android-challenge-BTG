package com.androidchallengebtg.activities.movieDetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidchallengebtg.R;
import com.androidchallengebtg.helpers.Tools;
import com.androidchallengebtg.helpers.connection.Connection;
import com.androidchallengebtg.helpers.connection.ConnectionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if(getIntent().getExtras()!=null){
            String extraMovie = getIntent().getStringExtra("movie");
            try {
                JSONObject movie = new JSONObject(extraMovie);
                fillScreen(movie);

                Connection connection = new Connection();
                int id = movie.getInt("id");
                connection.getMovie(id, new ConnectionListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        fillScreen(response);
                    }

                    @Override
                    public void onError(JSONObject error) {
                        try {
                            String message = error.getString("status_message");
                            Toast.makeText(MovieDetailsActivity.this,message,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                connection.getMovieAccountState(id, new ConnectionListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        Log.e(" account state", response.toString());
                        fillStatus(response);
                    }

                    @Override
                    public void onError(JSONObject error) {

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
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

    private void fillStatus(JSONObject status){
        FloatingActionButton floatingActionButton = findViewById(R.id.floatButtonFav);
        try {
            boolean favorite = status.getBoolean("favorite");
            if(favorite){
                floatingActionButton.setImageDrawable(this.getDrawable(R.drawable.heart_custom));
            }else{
                floatingActionButton.setImageDrawable(this.getDrawable(R.drawable.heart_outline_custom));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fillScreen(JSONObject movie){

        TextView tvTitle = findViewById(R.id.title);
        TextView tvOverview = findViewById(R.id.overview);
        TextView tvVoteAverage = findViewById(R.id.voteAverage);
        TextView tvGenres = findViewById(R.id.genres);

        ImageView ivBackdrop = findViewById(R.id.backdrop);
        String baseImageUrl = Tools.getBaseImageUrl("original");

        try {
            if(movie.has("title")){
                tvTitle.setText(movie.getString("title"));
            }else{
                tvTitle.setVisibility(View.GONE);
            }

            if(movie.has("overview")){
                tvOverview.setText(movie.getString("overview"));
            }else{
                tvOverview.setVisibility(View.GONE);
            }

            if(movie.has("vote_average")){
                tvVoteAverage.setText(movie.getString("vote_average"));
            }else{
                tvVoteAverage.setVisibility(View.GONE);
            }

            if(movie.has("backdrop_path")){
                String urlBackdrop = baseImageUrl+movie.getString("backdrop_path");
                Picasso.get().load(urlBackdrop).into(ivBackdrop);
            }else{
                ivBackdrop.setVisibility(View.GONE);
            }

            if(movie.has("genres")){
                StringBuilder genres = new StringBuilder();
                JSONArray genresArray = movie.getJSONArray("genres");
                for(int i = 0; i<=genresArray.length()-1; i++){
                    JSONObject genre = new JSONObject(genresArray.get(i).toString());
                    if(i == 0){
                        genres = new StringBuilder(genre.getString("name"));
                    }else{
                        genres.append(" ").append(genre.getString("name"));
                    }
                }
                tvGenres.setText(genres.toString());
                tvGenres.setVisibility(View.VISIBLE);
            }else{
                tvGenres.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
