package com.example.desafiobtg.usecases.movielist

import android.support.v4.app.Fragment
import com.example.desafiobtg.di.qualifiers.utils.BasePresenter
import com.example.desafiobtg.di.qualifiers.utils.BaseView

interface MovieListContract {

    interface View: BaseView<Presenter> {
        fun notifyDatasetChanged()
        fun notifyFavoriteChanged(index: Int)
        fun showMovieDetailsActivity(id: String?)
        fun showLoading(shouldShow: Boolean)
        fun showNoInternet(shouldShow: Boolean) {}
        fun showEmptyList(isEmpty: Boolean) {}
        fun onQueryTextChange(query: String)
        fun notifyItemInserted(position: Int) {}
        fun notifyItemRangeInserted(position: Int, count: Int)
        fun notifyItemChanged(position: Int)
        fun notifyItemRemoved(idx: Int)
    }

    interface Presenter: BasePresenter<View> {
        fun bindMovieHolder(movieHolder: MovieHolder?, position: Int)
        fun getListItemCount(): Int
        fun onViewCreated(fragment: Fragment)
        fun setMovieFavorite(position: Int, favorite: Boolean)
        fun bindFavoriteIcon(movieHolder: MovieHolder?, position: Int)
        fun setListType(listType: MovieListType)
        fun onItemClicked(position: Int)
        fun loadPopularMovieList()
        fun onQueryTextChange(query: String)

        fun loadMore()
        fun isLoadingMoreItems(): Boolean
        fun shouldLoadMoreItems(): Boolean
    }

    interface MovieHolder {
        fun setMoviePoster(posterUrl: String)
        fun setMovieTitle(movieTitle: String)
        fun setMovieYear(movieYear: String)
        fun setMovieFavorited(isFavorited: Boolean)
    }

}