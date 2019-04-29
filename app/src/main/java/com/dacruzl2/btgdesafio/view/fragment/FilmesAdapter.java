package com.dacruzl2.btgdesafio.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.dacruzl2.btgdesafio.model.pojos.Genre;
import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.FilmesViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private List<Genre> rootGenresList;

    public FilmesAdapter(Context context, List<Movie> movieList, List<Genre> rootGenresList) {
        this.context = context;
        this.movieList = movieList;
        this.rootGenresList = rootGenresList;

    }

    @NonNull
    @Override
    public FilmesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.filmes_item_list, parent, false);

        return new FilmesAdapter.FilmesViewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull FilmesViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        if(movie.getOverview().equals("")){
            movie.setOverview(context.getString(R.string.sinopse_no_description)); }

        holder.tvMovieName.setText(movie.getTitle());
        //holder.tvMovieYear.setText(String.format(movie.getReleaseDate(), new SimpleDateFormat("dd-MM-yyyy")));
        holder.progressBar.setVisibility(View.VISIBLE);

        try {
            holder.tvMovieYear.setText(dateToString(stringToDate(movie.getReleaseDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w300" + movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.no_image_24dp)
                .into(holder.ivMoviePoster);

    }


    //Format date to String
    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    //Format String to Date
    private Date stringToDate(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(dateString);
        return date;
    }
    @Override
    public int getItemCount() {

        return movieList.size();
    }

    class FilmesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMovieName)
        TextView tvMovieName;

        @BindView(R.id.tvMovieYear)
        TextView tvMovieYear;

        @BindView(R.id.ivMoviePoster)
        ImageView ivMoviePoster;

        //@BindView(R.id.ivOffFav)
        //MaterialFavoriteButton ivOffFav;

        @BindView(R.id.progress)
        ProgressBar progressBar;

        public FilmesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(Color.parseColor("#008577"), PorterDuff.Mode.SRC_IN);
        }
    }
}
