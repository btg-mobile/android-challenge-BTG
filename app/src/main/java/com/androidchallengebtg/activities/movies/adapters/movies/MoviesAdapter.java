package com.androidchallengebtg.activities.movies.adapters.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidchallengebtg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> {

    private Context context;
    private JSONArray list;

    public MoviesAdapter(Context context) {
        this.context = context;
        this.list = new JSONArray();
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
            Log.d("filme",list.get(i).toString());

            JSONObject item = list.getJSONObject(i);

            movieHolder.title.setText(item.getString("title"));
            movieHolder.releaseDate.setText(item.getString("release_date"));

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
