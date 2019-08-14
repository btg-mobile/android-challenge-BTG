package com.androidchallengebtg.activities.movies.adapters.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.androidchallengebtg.R;

class MovieHolder extends RecyclerView.ViewHolder {

    TextView title;

    MovieHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
    }
}
