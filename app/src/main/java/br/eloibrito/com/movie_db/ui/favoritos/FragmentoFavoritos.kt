package br.eloibrito.com.movie_db.ui.favoritos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.data.dao.AppDataBase
import br.eloibrito.com.movie_db.data.repositorios.RepositoriosMoviesFavoritos
import br.eloibrito.com.movie_db.models.Generos
import br.eloibrito.com.movie_db.models.Movies
import br.eloibrito.com.movie_db.ui.ListMoviesDetailsActivity
import br.eloibrito.com.movie_db.ui.RecycleViewItemClick
import br.eloibrito.com.movie_db.ui.adapter.RecycleFavoritos
import br.eloibrito.com.movie_db.utils.App
import kotlinx.android.synthetic.main.layout_lista_favoritos.view.*
import kotlinx.android.synthetic.main.layout_lista_movies.*
import javax.inject.Inject

class FragmentoFavoritos : Fragment(),
    RecycleViewItemClick, SearchView.OnQueryTextListener {

    @Inject
    lateinit var db: AppDataBase

    lateinit var viewFrag : View

    init {
        (App).getComponent().inject(this)
    }

    lateinit var viewModel: ListMovieFavoritosModel
    private var lista_favoritos = ArrayList<Movies>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFrag = inflater.inflate(R.layout.layout_lista_favoritos, container, false)
        return viewFrag
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        controles()
    }

    fun controles() {

        viewModel = ViewModelProviders.of(
            this,
            ListMovieFavoritosModelFactory(
                RepositoriosMoviesFavoritos(
                    db
                )
            )
        ).get(ListMovieFavoritosModel::class.java)

        val mLayoutManagerM = GridLayoutManager(context!!, 2)

        val recycle_favoritos = viewFrag.findViewById<RecyclerView>(R.id.recycle_favoritos)
        recycle_favoritos.setHasFixedSize(true)
        recycle_favoritos.layoutManager = mLayoutManagerM

        val mRecycleMovies = RecycleFavoritos(
            lista_favoritos,
            this
        )

        recycle_favoritos.adapter = mRecycleMovies

        carregar_view_model()

    }

    fun carregar_view_model() {
        viewModel.pegar_favoritos(context!!)

        viewModel._isLoading.observe(this, Observer {
            if (it!!) progress.visibility = View.VISIBLE else progress.visibility = View.GONE
        })

        viewModel._listaFavoritos.observe(this, Observer { it ->
            lista_favoritos.clear()
            lista_favoritos.addAll(it)
            (viewFrag.recycle_favoritos.adapter as RecycleFavoritos).notifyDataSetChanged()
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if(isVisibleToUser)
            carregar_view_model()

    }

    override fun onClickGeneros(view: View, generos: Generos, pos: Int) {

    }

    override fun onClickMovies(view: View, movies: Movies, pos: Int) {
        val intent = Intent(context!!, ListMoviesDetailsActivity::class.java)
        intent.putExtra(ListMoviesDetailsActivity.ID_DETAIL, movies)

        val activityOptions: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!,


                Pair<View, String>(
                    view,
                    ListMoviesDetailsActivity.VIEW_NAME_HEADER_IMAGE
                )
            )

        ActivityCompat.startActivity(context!!, intent, activityOptions.toBundle())
    }

    override fun onClickMoviesLike(movies: Movies, pos: Int, like: Boolean) {

    }

    override fun onClear() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater = activity!!.menuInflater
        inflater.inflate(R.menu.menu_buscar, menu)

        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.buscar).actionView as SearchView


        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(activity!!.componentName)
        )

        searchView.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        try {

            val adapter = viewFrag.recycle_favoritos.adapter as RecycleFavoritos
            adapter.filter.filter(p0)
            return true
        } catch (err: Exception) {
            err.printStackTrace()
        }

        return false
    }
}