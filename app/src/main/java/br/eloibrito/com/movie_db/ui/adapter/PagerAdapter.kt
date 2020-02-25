package br.eloibrito.com.movie_db.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.eloibrito.com.movie_db.ui.favoritos.FragmentoFavoritos
import br.eloibrito.com.movie_db.ui.filmes.FragmentoFilmes

class PagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> FragmentoFilmes()
            else -> return FragmentoFavoritos()
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Filmes"
            else -> return "Favoritos"
        }
    }
}