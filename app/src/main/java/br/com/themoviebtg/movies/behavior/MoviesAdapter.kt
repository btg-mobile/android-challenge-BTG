package br.com.themoviebtg.movies.behavior

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.themoviebtg.R
import br.com.themoviebtg.movies.model.MovieModel
import com.nostra13.universalimageloader.core.ImageLoader


class MoviesAdapter(private val movieModelList: List<MovieModel>) :
    BaseAdapter() {

    private val theMovieDbImagesUrl = "http://image.tmdb.org/t/p/w300/"


    private fun inflateView(inflater: LayoutInflater, viewGroup: ViewGroup): View {
        return inflater.inflate(R.layout.gv_item_movie, viewGroup, false)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = this.inflateView(inflater, parent)
        val movieModel = this.movieModelList[position]


        val movieTitle = view.findViewById<TextView>(R.id.tv_movie_item_title)
        movieTitle.text = movieModel.original_title

        val movieYear = view.findViewById<TextView>(R.id.tv_movie_release_year)
        val movieReleaseYear = "(${movieModel.release_date?.split("-")?.get(0) ?: "-"})"
        movieYear.text = movieReleaseYear



        val movieImage = view.findViewById<ImageView>(R.id.iv_movie_item_cover)


        initMoviePoster(movieModel, movieImage)
        return view
    }

    private fun initMoviePoster(
        movieModel: MovieModel,
        movieImage: ImageView
    ) {
        val imageLoader = ImageLoader.getInstance()
        val imageUrl = "${theMovieDbImagesUrl}${movieModel.poster_path}"

        imageLoader.displayImage(imageUrl, movieImage)
        movieImage.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun getItem(position: Int) = this.movieModelList[position]

    override fun getItemId(position: Int) = this.movieModelList[position].longId

    override fun getCount() = this.movieModelList.size


}