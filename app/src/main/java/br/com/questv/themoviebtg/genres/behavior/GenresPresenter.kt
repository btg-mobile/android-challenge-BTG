package br.com.questv.themoviebtg.genres.behavior

import br.com.questv.themoviebtg.genres.model.GenreModel
import br.com.questv.themoviebtg.genres.ui.GenresView

class GenresPresenter(var genresView: GenresView) : GenresInteractor.GenreFetchListener {

    private val moviesInteractor = GenresInteractor()

    fun fetchAllGenres() {
        this.genresView.showProgress()
        this.moviesInteractor.fetchAllGenres(this)
    }

    override fun onGenreFetchedSuccess(genreModelList: List<GenreModel>) {
        val adapter = GenresAdapter(genreModelList)
        this.genresView.initRecyclerView(adapter)
        this.genresView.hideProgress()
    }

    override fun onGenreFetchedFail(exception: Exception) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}