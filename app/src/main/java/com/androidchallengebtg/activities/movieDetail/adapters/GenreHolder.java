package com.androidchallengebtg.activities.movieDetail.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.androidchallengebtg.R;
import com.androidchallengebtg.helpers.interfaces.ItemViewHolderClickListener;

class GenreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name;

    private ItemViewHolderClickListener clickListener;
    private int position;

    GenreHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);

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
