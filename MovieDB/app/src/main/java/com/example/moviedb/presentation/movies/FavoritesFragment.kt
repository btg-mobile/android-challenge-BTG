package com.example.moviedb.presentation.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.presentation.details.MovieDetailsActivity
import com.example.moviedb.presentation.repository.DataRepository
import kotlinx.android.synthetic.main.fragment_movies.*


class FavoritesFragment : Fragment() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies, container, false)


    override fun onStart() {
        super.onStart()

        movies = DataRepository.getFavoriteMovies()

        mHandler = Handler()
        swiperMovies.isRefreshing = true
        mRunnable = Runnable {
            swiperMovies.isRefreshing = false
        }
        updateView()

        swiperMovies.setOnRefreshListener {
            movies = DataRepository.getFavoriteMovies()
            updateView()
            mRunnable = Runnable {
                swiperMovies.isRefreshing = false
            }
        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)

        if(menuVisible){
            movies = DataRepository.getFavoriteMovies()
            updateView()
        }
    }

    private fun updateView() {

        if (movies.isNotEmpty()) {
            with(recyclerMovies) {
                layoutManager =
                    GridLayoutManager(
                        context,
                        2
                    )
                setHasFixedSize(true)
                adapter = MoviesAdapter(movies) { movie ->
                    val intent = MovieDetailsActivity.getStartIntent(
                        context,
                        movie.id,
                        movie.title,
                        movie.overview,
                        movie.posterPath,
                        movie.voteAverage,
                        movie.genres
                    )
                    this.context.startActivity(intent)
                }
            }
        }

        if (movies.isEmpty()) {
            no_favorite.visibility = View.VISIBLE
            swiperMovies.visibility = View.INVISIBLE

        } else {
            no_favorite.visibility = View.INVISIBLE
            swiperMovies.visibility = View.VISIBLE
        }
        mHandler.post(mRunnable)

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        DataRepository.loadData(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_search, menu)
        val searchItem = menu.findItem(R.id.movie_search)

        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true
                @SuppressLint("DefaultLocale")
                override fun onQueryTextChange(newText: String?): Boolean {
                    movies = if (newText!!.isNotEmpty()) {
                        val search = newText.toLowerCase()
                        DataRepository.searchFavoriteMoviesByYearOrTitle(search)

                    } else {
                        DataRepository.getFavoriteMovies()
                    }

                    updateView()

                    return true
                }
            })

        }

        return super.onCreateOptionsMenu(menu, inflater)
    }
}
