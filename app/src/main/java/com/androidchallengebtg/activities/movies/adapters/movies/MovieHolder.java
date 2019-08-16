package com.androidchallengebtg.activities.movies.adapters.movies;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidchallengebtg.R;
import com.androidchallengebtg.application.ApplicationBTG;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderClickListener;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderFavIconClickListner;

class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    TextView title;
    TextView releaseDate;
    ImageView poster;
    ImageView favIcon;
    private ItemViewHolderClickListener clickListener;
    private ItemViewHolderFavIconClickListner itemViewHolderFavIconClickListner;
    private int position;

    MovieHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        releaseDate = itemView.findViewById(R.id.releaseDate);
        poster = itemView.findViewById(R.id.poster);
        favIcon = itemView.findViewById(R.id.imageViewFav);
        CardView cardView = itemView.findViewById(R.id.cardViewMovie);

        favIcon.setOnClickListener(this);
        favIcon.setImageTintList(ContextCompat.getColorStateList(ApplicationBTG.getContext(), R.color.colorAccent));

        cardView.setOnLongClickListener(this);

        itemView.setOnClickListener(this);
    }

    void setPosition(int position) {
        this.position = position;
    }

    void setClickListener(ItemViewHolderClickListener clickListener) {
        this.clickListener = clickListener;
    }

    void setItemViewHolderFavIconClickListner(ItemViewHolderFavIconClickListner itemViewHolderFavIconClickListner) {
        this.itemViewHolderFavIconClickListner = itemViewHolderFavIconClickListner;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageViewFav){
            if(this.itemViewHolderFavIconClickListner != null){
                itemViewHolderFavIconClickListner.onClick(this.position);
            }
        }else{
            if(clickListener != null){
                clickListener.onClick(this.position);
            }
        }

    }

    @Override
    public boolean onLongClick(View v) {
        if(v.getId() == R.id.cardViewMovie){
            if(clickListener!=null){
                clickListener.onLongClick(this.position);
            }
        }else{
            if(clickListener!=null){
                clickListener.onLongClick(this.position);
            }
        }

        return true;
    }
}
