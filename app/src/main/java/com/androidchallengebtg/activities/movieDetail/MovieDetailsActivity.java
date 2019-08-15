package com.androidchallengebtg.activities.movieDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidchallengebtg.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(getIntent().getExtras()!=null){
            String extraMovie = getIntent().getStringExtra("movie");
            try {
                JSONObject movie = new JSONObject(extraMovie);
                Log.e("MOVIE",movie.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
