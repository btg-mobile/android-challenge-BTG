package com.dacruzl2.btgdesafio.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dacruzl2.btgdesafio.R;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public FavoritosAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public FavoritosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritos_item_list, parent, false);

        return new FavoritosAdapter.FavoritosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritosViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.tvMovieNameFav.setText(movie.getTitle());
        holder.tvMovieYearFav.setText(movie.getReleaseDate());
        holder.progressFav.setVisibility(View.VISIBLE);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w300" + movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.no_image_24dp)
                .into(holder.ivMoviePosterFav);
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        } else {
            return 0;
        }
    }

    class FavoritosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMovieNameFav)
        TextView tvMovieNameFav;

        @BindView(R.id.tvMovieYearFav)
        TextView tvMovieYearFav;

        @BindView(R.id.ivMoviePosterFav)
        ImageView ivMoviePosterFav;

        //@BindView(R.id.ivOffFavFragment)
       // ImageView ivOffFavFragment;

        @BindView(R.id.progressFav)
        ProgressBar progressFav;

        public FavoritosViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
  /*  if (resultListFav != null) {
            Movie current = resultListFav.get(position);
            holder.wordItemView.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No data!");
        }*/