package com.dacruzl2.btgdesafio.view.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dacruzl2.btgdesafio.R;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFilmesAdapter extends RecyclerView.Adapter<SearchFilmesAdapter.SearchFilmesViewHolder> {

    private Context context;
    private List<Movie> searchFilmesList;

    public SearchFilmesAdapter(Context context, List<Movie> searchFilmesList) {
        this.context = context;
        this.searchFilmesList = searchFilmesList;
    }

    @NonNull
    @Override
    public SearchFilmesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_filmes_item_list, parent, false);

        return new SearchFilmesAdapter.SearchFilmesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFilmesViewHolder holder, int position) {
        Movie movie = searchFilmesList.get(position);

        holder.tvMovieNameSearchFilmes.setText(movie.getTitle());
        holder.tvMovieYearSearchFilmes.setText(movie.getReleaseDate());
        Glide.with(holder.itemView.getContext())
                .load(movie.getPosterPath())
                .into(holder.ivMoviePosterSearchFimes);
    }

    @Override
    public int getItemCount() {
        return searchFilmesList.size();
    }

    class SearchFilmesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMovieNameSearchFilmes)
        TextView tvMovieNameSearchFilmes;

        @BindView(R.id.tvMovieYearSearchFilmes)
        TextView tvMovieYearSearchFilmes;

        @BindView(R.id.ivMoviePosterSearchFimes)
        ImageView ivMoviePosterSearchFimes;

        public SearchFilmesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
