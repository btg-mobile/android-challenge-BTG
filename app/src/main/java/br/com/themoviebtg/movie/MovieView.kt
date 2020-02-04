package br.com.themoviebtg.movie

interface MovieView {
    fun showProgress()
    fun hideProgress()
    fun attachMovieOverview(overview: String)
    fun attachMovieBackground(posterUrl: String)
    fun attachMovieGenres(genres: List<String>)
    fun attachMovieTitle(title: String?)
}