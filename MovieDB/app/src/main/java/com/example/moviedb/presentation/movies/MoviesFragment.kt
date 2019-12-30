package com.example.moviedb.presentation.movies


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.presentation.details.MovieDetailsActivity
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: MoviesViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        viewModel.moviesData.observe(this, Observer {
            it?.let { movies ->
                with(recyclerMovies) {
                    layoutManager =
                        LinearLayoutManager(
                            context,
                            RecyclerView.VERTICAL,
                            false
                        )
                    setHasFixedSize(true)
                    adapter = MoviesAdapter(movies) { movie ->
                        val intent = MovieDetailsActivity.getStartIntent(
                            context,
                            movie.id,
                            movie.title,
                            movie.overview,
                            movie.posterPath,
                            movie.voteAverage
                        )
                        this.context.startActivity(intent)
                    }
                }
            }
        })

        viewModel.getGenres()
        viewModel.getMovies()

        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val viewModel: MoviesViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        inflater.inflate(R.menu.main_search, menu)
        val searchItem = menu?.findItem(R.id.movie_search)

        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        val search = newText.toLowerCase()
                        viewModel.searchMovieByTitle(search)
                    }

                    return true
                }
            })

        }

        return super.onCreateOptionsMenu(menu, inflater)
    }


}
