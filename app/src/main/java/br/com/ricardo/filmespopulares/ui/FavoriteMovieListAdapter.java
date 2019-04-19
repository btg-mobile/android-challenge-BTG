package br.com.ricardo.filmespopulares.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.ricardo.filmespopulares.R;
import br.com.ricardo.filmespopulares.data.network.model.Film;

public class FavoriteMovieListAdapter extends RecyclerView.Adapter<FavoriteMovieListAdapter.FavoriteMovieListViewHolder> {

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private List<Film> movieLists;

    //Atributo da interface.
    private static OnItemClickListener clickListener;

    //Contrutor do adapter
    public FavoriteMovieListAdapter() {
        movieLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public FavoriteMovieListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite, viewGroup, false);

        FavoriteMovieListViewHolder holder = new FavoriteMovieListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieListViewHolder FavoriteMovieListViewHolder, int i) {

        Picasso.with(FavoriteMovieListViewHolder.imageMoviePoster.getContext())
                .load(IMAGE_BASE_URL + movieLists.get(i).getPosterPath())
                .into(FavoriteMovieListViewHolder.imageMoviePoster);

        FavoriteMovieListViewHolder.textMovieTitle.setText(movieLists.get(i).getTitle());
        FavoriteMovieListViewHolder.textMovieReleaseDate.setText(movieLists.get(i).getReleaseDate());

    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }


    public class FavoriteMovieListViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageMoviePoster;
        private TextView textMovieTitle;
        private TextView textMovieReleaseDate;

        public FavoriteMovieListViewHolder(@NonNull View itemView) {
            super(itemView);

            imageMoviePoster = (ImageView) itemView.findViewById(R.id.image_movie_detail);
            textMovieTitle = (TextView) itemView.findViewById(R.id.item_movie_title);
            textMovieReleaseDate = (TextView) itemView.findViewById(R.id.item_movie_release_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(clickListener != null){
                        clickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setFavoriteFilm(List<Film> film){
        this.movieLists = film;
        notifyDataSetChanged();
    }

    //Interface criada para ser chamada na MainActivity(MovieList) passando um filme no parâmetro.
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    //Método utilizado para comunicar o evento de clique de volta para a Activity
    public void setOnItemClickListener(OnItemClickListener listener){
        clickListener = listener;
    }
}