package br.com.themoviebtg.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import br.com.questv.themoviebtg.R
import br.com.themoviebtg.favorites.interaction.FavoritesPresenter
import br.com.themoviebtg.favorites.recycler.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(),
    FavoritesView {
    private val presenter =
        FavoritesPresenter(this)
    private lateinit var favoritesGridView: GridView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)!!
        this.favoritesGridView = view.findViewById(R.id.gv_favorites)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.presenter.fetchFavorites()
    }

    override fun showProgress() {
        pb_favorites.visibility = View.VISIBLE
    }

    override fun initGridView(favoritesAdapter: FavoritesAdapter) {
        this.favoritesGridView.adapter = favoritesAdapter
    }

    override fun hideProgress() {
        pb_favorites.visibility = View.GONE
    }

}