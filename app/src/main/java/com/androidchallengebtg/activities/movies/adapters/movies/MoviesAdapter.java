package com.androidchallengebtg.activities.movies.adapters.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidchallengebtg.R;
import com.androidchallengebtg.helpers.Tools;
import com.androidchallengebtg.helpers.interfaces.EndlessScrollListener;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderClickListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> {

    private Context context;
    private JSONArray list;
    private String baseImageUrl;
    private ItemViewHolderClickListener clickListener;
    private EndlessScrollListener endlessScrollListener;

    public MoviesAdapter(Context context) {
        this.context = context;
        this.list = new JSONArray();
        this.baseImageUrl = Tools.getBaseImageUrl(185);
    }

    public void setClickListener(ItemViewHolderClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setEndlessScrollListener(EndlessScrollListener endlessScrollListener) {
        this.endlessScrollListener = endlessScrollListener;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(this.context)
                .inflate(R.layout.item_list_movies, viewGroup, false);
        MovieHolder movieHolder = new MovieHolder(itemView);
        movieHolder.setClickListener(this.clickListener);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {
        movieHolder.setPosition(position);
        try {
            JSONObject item = list.getJSONObject(position);
            String urlPoster = baseImageUrl+item.getString("poster_path");
            String releaseDate = item.getString("release_date");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date convertedCurrentDate = sdf.parse(releaseDate);

            SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            String date = sdf2.format(convertedCurrentDate );

            movieHolder.title.setText(item.getString("title"));
            movieHolder.releaseDate.setText(date);
            Picasso.get().load(urlPoster).into(movieHolder.poster);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(position == list.length()-1){
            this.endlessScrollListener.onEndReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return this.list.length();
    }

    public void setList(JSONArray list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void putList(JSONArray list){
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

    public void clearList(){
        this.list = new JSONArray();
        notifyDataSetChanged();
    }

    public JSONObject getItem(int position){
        JSONObject jsonObject = null;
        try {
            jsonObject = list.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
