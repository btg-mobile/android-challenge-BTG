package com.curymorais.moviedbapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.curymorais.moviedbapp.data.domain.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_row_movie.*
import androidx.appcompat.app.AppCompatActivity
import com.curymorais.moviedbapp.moviedetail.MovieDetailFragment

class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesAdapter.MovieHolder>() {

    private var movieList : ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false))
    }

    override fun getItemCount(): Int {
       return movieList.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class MovieHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(movieDetail: Movie){

            Picasso.get().load(BuildConfig.BASE_LOGO+movieDetail.posterPath).into(movie_image)
            movie_name.text = movieDetail.title
            movie_year.text =  movieDetail.releaseDate
            containerView.setOnClickListener(View.OnClickListener {
                val activity = containerView.getContext() as AppCompatActivity
                val myFragment = MovieDetailFragment(movieDetail)
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit()
            })
        }
    }

    fun updateList(gameList: ArrayList<Movie>){
        this.movieList = gameList
    }
}