package com.androidchallengebtg.activities.movieDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidchallengebtg.R;
import com.androidchallengebtg.helpers.Tools;
import com.squareup.picasso.Picasso;

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
                fillScreen(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillScreen(JSONObject movie){

        TextView title = findViewById(R.id.title);
        ImageView backdrop = findViewById(R.id.backdrop);
        String baseImageUrl = Tools.getBaseImageUrl("original");

        try {
            title.setText(movie.getString("title"));
            String urlBackdrop = baseImageUrl+movie.getString("backdrop_path");
            Log.e("url",urlBackdrop);
            Picasso.get().load(urlBackdrop).into(backdrop);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
