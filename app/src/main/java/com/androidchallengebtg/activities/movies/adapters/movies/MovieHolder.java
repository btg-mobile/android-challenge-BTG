package com.androidchallengebtg.activities.movies.adapters.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidchallengebtg.R;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderClickListener;

class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title;
    TextView releaseDate;
    ImageView poster;
    ImageView favIcon;

    private ItemViewHolderClickListener clickListener;
    private int position;

    MovieHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        releaseDate = itemView.findViewById(R.id.releaseDate);
        poster = itemView.findViewById(R.id.poster);
        favIcon = itemView.findViewById(R.id.imageViewFav);

        itemView.setOnClickListener(this);
    }

    void setPosition(int position) {
        this.position = position;
    }

    void setClickListener(ItemViewHolderClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if(clickListener!=null){
            clickListener.onClick(this.position);
        }
    }
}
