package br.com.questv.themoviebtg.ui.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.questv.themoviebtg.R

class FavoritesFragment : Fragment(), FavoritesView {
    private val presenter = FavoritesPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_favorites, container, false)!!

}