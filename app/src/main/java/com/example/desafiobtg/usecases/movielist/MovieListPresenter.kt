package com.example.desafiobtg.usecases.movielist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import com.example.desafiobtg.data.Repository
import com.example.desafiobtg.db.entities.Movie
import com.example.desafiobtg.utils.DateUtils
import com.example.desafiobtg.utils.ListUtils
import javax.inject.Inject

class MovieListPresenter @Inject constructor(private val mRepository: Repository): MovieListContract.Presenter {

    private var mMovieListView: MovieListContract.View? = null

    private var mMovieList = arrayListOf<Movie>()
    private var mFilteredMovieList = arrayListOf<Movie>()
    private var mFavoriteMovieIds = listOf<String>()

    private var mQuery = ""

    private var listType = MovieListType.POPULAR

    private var mFavoriteIdsLiveData: LiveData<List<String>>? = null
    private var mFavoriteMoviesLiveData: LiveData<List<Movie>?>? = null

    override fun takeView(view: MovieListContract.View) {
        mMovieListView = view
    }

    override fun dropView() {
        mMovieListView = null
    }

    override fun bindMovieHolder(movieHolder: MovieListContract.MovieHolder?, position: Int) {
        // TODO: Adicionar a uma variável
        movieHolder?.setMoviePoster("https://image.tmdb.org/t/p/w185${mFilteredMovieList[position].posterUrl}")
        mFilteredMovieList[position].title?.let { title -> movieHolder?.setMovieTitle(title) }
        val year = DateUtils.getYearForDate(mFilteredMovieList[position].releaseDate)
        year?.let { movieHolder?.setMovieYear(it) }
        movieHolder?.setMovieFavorited(mFavoriteMovieIds.contains(mFilteredMovieList[position].id))
    }

    override fun setMovieFavorite(position: Int, favorite: Boolean) {
        val id = mFilteredMovieList[position].id
        if (favorite) {
            mRepository.addFavoriteMovie(id)
        } else {
            mRepository.removeFavoriteMovie(id)
        }
    }

    override fun onViewCreated(fragment: Fragment) {
        loadFavoriteIds(fragment)
        when (listType) {
            MovieListType.POPULAR -> loadPopularMovieList()
            MovieListType.FAVORITE -> loadFavoriteMovieList(fragment)
        }
    }

    private fun loadFavoriteIds(fragment: Fragment) {
        mFavoriteIdsLiveData = mRepository.getFavoriteIds()
        mFavoriteIdsLiveData?.observe(fragment, Observer { idList ->
            idList?.let { ids ->
                val uncommonItems = ListUtils.getUncommonItems(mFavoriteMovieIds, ids)
                uncommonItems.forEach { idToNotify ->
                    val movieToUpdate = mFilteredMovieList.find { it.id == idToNotify }
                    val indexToUpdate = mFilteredMovieList.indexOf(movieToUpdate)
                    mMovieListView?.notifyFavoriteChanged(indexToUpdate)
                }

                mFavoriteMovieIds = ids
            }
        })
    }

    override fun loadPopularMovieList() {
        mMovieListView?.showLoading(true)
        mRepository.getMovieList(1, { response ->
            response.let {
                mMovieList = ArrayList(it.movieList)
                mFilteredMovieList = ArrayList(getFilteredListForQuery(mQuery))
            }
            mMovieListView?.notifyDatasetChanged()
            mMovieListView?.showLoading(false)
        }, {
            mMovieListView?.showLoading(false)
            mMovieListView?.showNoInternet(true)
        })
    }

    private fun loadFavoriteMovieList(fragment: Fragment) {
        mMovieListView?.showLoading(true)
        mFavoriteMoviesLiveData = mRepository.getFavoriteMovieList()
        mFavoriteMoviesLiveData?.observe(fragment, Observer { favList ->
            favList?.let {
                mMovieList = ArrayList(it)
                mFilteredMovieList = ArrayList(getFilteredListForQuery(mQuery))
                // TODO: Fazer um notifyItemInserted ou um notifyItemRemoved
                // para fazer animação bonitinha do item sendo removido
                mMovieListView?.notifyDatasetChanged()

                mMovieListView?.showEmptyList(mFilteredMovieList.isEmpty())
                mMovieListView?.showLoading(false)
            }
        })
    }

    override fun setListType(listType: MovieListType) {
        this.listType = listType
    }

    override fun bindFavoriteIcon(movieHolder: MovieListContract.MovieHolder?, position: Int) {
        movieHolder?.setMovieFavorited(mFavoriteMovieIds.contains(mFilteredMovieList[position].id))
    }

    override fun onItemClicked(position: Int) {
        mMovieListView?.showMovieDetailsActivity(mFilteredMovieList.getOrNull(position)?.id)
    }

    override fun getMovieCount() = mFilteredMovieList.size

    override fun onQueryTextChange(query: String) {
        mQuery = query
        mFilteredMovieList = ArrayList(getFilteredListForQuery(query))
        mMovieListView?.notifyDatasetChanged()
    }

    private fun getFilteredListForQuery(query: String): List<Movie> {
        if (query.isEmpty()) return ArrayList<Movie>(mMovieList)

        val list = ArrayList<Movie>()
        val lowerCaseQuery = query.toLowerCase()
        mMovieList.forEach { movie ->
            val validTitle = (movie.title?.toLowerCase() ?: "").contains(lowerCaseQuery)

            val year = DateUtils.getYearForDate(movie.releaseDate)?.toIntOrNull()
            val queryYear = query.toIntOrNull()
            val validYear = year == queryYear
            if (validTitle || validYear) {
                list.add(movie)
            }
        }

        return list
    }
}