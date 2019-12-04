package com.example.android_challenge_btg.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_challenge_btg.R;
import com.example.android_challenge_btg.models.Genre;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder>{

    private View view;
    private List<Genre> genres;

    public GenresAdapter(List<Genre> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_genre, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genre genre = this.genres.get(holder.getAdapterPosition());
        holder.genre = genre;

        holder.genreName.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        return this.genres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        Genre genre;

        TextView genreName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            this.genreName = view.findViewById(R.id.genre_name);
        }
    }
}
