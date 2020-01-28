package com.rafaelpereiraramos.challengebtg.view.search

import android.widget.Filter
import com.rafaelpereiraramos.challengebtg.commonsandroid.DateUtils
import com.rafaelpereiraramos.challengebtg.repository.model.Movie

class FavouritesMovieFilter(
    private val originalData: List<Movie>,
    private val publish: (List<Movie>) -> Unit
) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults? {
        val filterResults = FilterResults()
        val matchedMovies = mutableListOf<Movie>()

        val query = constraint.toString().trim()

        if (query.isEmpty())
            return null

        val arguments = query.split(Regex.fromLiteral("\\s+"))

        for (movie in originalData) {
            if (isMatch(movie, arguments)) {
                matchedMovies.add(movie)
            }
        }

        filterResults.count = matchedMovies.size
        filterResults.values = matchedMovies
        return filterResults
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        @Suppress("UNCHECKED_CAST")
        when {
            results == null -> publish.invoke(originalData)
            results.values == null -> publish.invoke(emptyList())
            else -> publish.invoke(results.values as List<Movie>)
        }
    }

    private fun isMatch(movie: Movie, arguments: List<String>): Boolean {
        for (search in arguments) {
            if (!movie.title?.toLowerCase()?.contains(search)!! &&
                !DateUtils.getMovieYear(movie.releaseDate!!).toString().contains(search)) {
                return false
            }
        }

        return true
    }
}