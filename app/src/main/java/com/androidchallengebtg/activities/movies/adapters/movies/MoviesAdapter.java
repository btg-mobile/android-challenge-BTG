package com.androidchallengebtg.activities.movies.adapters.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidchallengebtg.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> {

    private Context context;
    private JSONArray list;
    private String baseImageUrl;

    public MoviesAdapter(Context context) {
        this.context = context;
        this.list = new JSONArray();
        this.baseImageUrl = context.getString(R.string.tmdb_images_base_url);
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(this.context)
                .inflate(R.layout.item_list_movies, viewGroup, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        try {
            JSONObject item = list.getJSONObject(i);
            movieHolder.title.setText(item.getString("title"));
            movieHolder.releaseDate.setText(item.getString("release_date"));
            String url = baseImageUrl+item.getString("poster_path");
            Picasso.get().load(url).into(movieHolder.poster);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.list.length();
    }

    public void setList(JSONArray list) {
        for(int i = 0; i<list.length(); i++){
            try {
                JSONObject jsonObject = list.getJSONObject(i);
                this.list.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }
}
