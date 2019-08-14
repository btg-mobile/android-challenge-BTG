package com.androidchallengebtg.activities.movies.adapters.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidchallengebtg.R;

class MovieHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView releaseDate;
    ImageView poster;

    MovieHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        releaseDate = itemView.findViewById(R.id.releaseDate);
        poster = itemView.findViewById(R.id.poster);
    }
}
