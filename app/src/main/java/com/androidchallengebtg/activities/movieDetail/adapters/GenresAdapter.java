package com.androidchallengebtg.activities.movieDetail.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidchallengebtg.R;
import com.androidchallengebtg.helpers.interfaces.EndlessScrollListener;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GenresAdapter extends RecyclerView.Adapter<GenreHolder> {

    private Context context;
    private JSONArray list;
    private ItemViewHolderClickListener clickListener;
    private EndlessScrollListener endlessScrollListener;

    public GenresAdapter(Context context) {
        this.context = context;
        this.list = new JSONArray();
    }

    public void setClickListener(ItemViewHolderClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setEndlessScrollListener(EndlessScrollListener endlessScrollListener) {
        this.endlessScrollListener = endlessScrollListener;
    }

    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(this.context)
                .inflate(R.layout.item_list_genre, viewGroup, false);
        GenreHolder movieHolder = new GenreHolder(itemView);
        movieHolder.setClickListener(this.clickListener);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder genreHolder, int position) {
        genreHolder.setPosition(position);
        try {
            JSONObject item = list.getJSONObject(position);
            genreHolder.name.setText(item.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(this.endlessScrollListener!=null){
            if(position == list.length()-1){
                this.endlessScrollListener.onEndReached(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.list.length();
    }

    public void setList(JSONArray list) {
        this.list = list;
        Log.e("generos",list.toString());
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
