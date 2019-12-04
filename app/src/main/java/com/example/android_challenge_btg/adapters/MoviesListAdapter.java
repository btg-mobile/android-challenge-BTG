package com.example.android_challenge_btg.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_challenge_btg.Activities.DetailsActivity;
import com.example.android_challenge_btg.R;
import com.example.android_challenge_btg.models.Movie;
import com.j256.ormlite.stmt.query.In;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    private View view;
    private List<Movie> movieList;
    private final String imageUrl = "http://image.tmdb.org/t/p/w185/";
    Context context;

    public MoviesListAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = this.movieList.get(holder.getAdapterPosition());
        holder.movie = movie;

        Glide.with(holder.view).load(imageUrl + movie.getPosterPath()).into(holder.poster);
        holder.movieName.setText(movie.getTitle());
        holder.movieYear.setText(movie.getReleaseDate());

        holder.movieCard.setOnClickListener(v -> {
            Intent detailsIntent = new Intent(context, DetailsActivity.class);
            detailsIntent.putExtra("movie", holder.movie);
            context.startActivity(detailsIntent);
        });
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        Movie movie;

        ConstraintLayout movieCard;
        ImageView poster;
        TextView movieName;
        TextView movieYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.view = itemView;
            this.movieCard = view.findViewById(R.id.movie_card);
            this.poster = view.findViewById(R.id.poster_img);
            this.movieName = view.findViewById(R.id.movie_name_tv);
            this.movieYear = view.findViewById(R.id.movie_year_tv);
        }
    }
}
