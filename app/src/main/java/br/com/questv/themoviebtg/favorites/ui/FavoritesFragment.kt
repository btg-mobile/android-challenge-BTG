package br.com.questv.themoviebtg.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.favorites.interaction.FavoritesPresenter
import br.com.questv.themoviebtg.favorites.recycler.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(),
    FavoritesView {
    private val presenter = FavoritesPresenter(this)
    private lateinit var favoritesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)!!
        this.favoritesRecyclerView = view.findViewById(R.id.rv_favorites)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.presenter.fetchFavorites()
    }

    override fun showProgress() {
        pb_favorites.visibility = View.VISIBLE
    }

    override fun initRecyclerView(favoritesAdapter: FavoritesAdapter) {
        this.favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        this.favoritesRecyclerView.adapter = favoritesAdapter
    }

    override fun hideProgress() {
        pb_favorites.visibility = View.GONE
    }

}