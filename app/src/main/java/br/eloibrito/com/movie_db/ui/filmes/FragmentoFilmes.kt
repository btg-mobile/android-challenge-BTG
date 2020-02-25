package br.eloibrito.com.movie_db.ui.filmes

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.data.dao.AppDataBase
import br.eloibrito.com.movie_db.data.network.ApiRetrofit
import br.eloibrito.com.movie_db.data.network.Callback
import br.eloibrito.com.movie_db.data.repositorios.RepositoriosMovies
import br.eloibrito.com.movie_db.models.Generos
import br.eloibrito.com.movie_db.models.Movies
import br.eloibrito.com.movie_db.ui.ListMoviesDetailsActivity
import br.eloibrito.com.movie_db.ui.RecycleViewItemClick
import br.eloibrito.com.movie_db.ui.adapter.PaginationScrolled
import br.eloibrito.com.movie_db.ui.adapter.RecycleGeneros
import br.eloibrito.com.movie_db.ui.adapter.RecycleMovies
import br.eloibrito.com.movie_db.utils.App
import br.eloibrito.com.movie_db.utils.Utils
import kotlinx.android.synthetic.main.layout_lista_movies.*
import kotlinx.android.synthetic.main.layout_lista_movies.view.*
import kotlinx.android.synthetic.main.layout_movie.*
import kotlinx.android.synthetic.main.layout_movie.principal
import java.lang.Exception
import javax.inject.Inject

class FragmentoFilmes : Fragment(), Callback, View.OnClickListener,
    RecycleViewItemClick, SearchView.OnQueryTextListener {

    @Inject
    lateinit var api: ApiRetrofit
    @Inject
    lateinit var db: AppDataBase

    lateinit var viewFrag : View


    init {
        (App).getComponent().inject(this)
    }


    lateinit var viewModel: ListMovieModel
    private var lista_movies = ArrayList<Movies>()
    private var page: Int? = 1
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFrag = inflater.inflate(R.layout.layout_lista_movies, container, false)
        return viewFrag
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        controles()
    }

    fun controles() {

        viewModel = ViewModelProviders.of(
            this, ListMovieModelFactory(
                RepositoriosMovies(
                    (api),
                    db
                )
            )
        ).get(ListMovieModel::class.java)

        fab_atualizar.setOnClickListener(this)
        call_view_model()
    }


    @SuppressLint("RestrictedApi")
    fun call_view_model() {

        val mLayoutManagerM = GridLayoutManager(context!!, 2)

        val recycle_movies = viewFrag.findViewById<RecyclerView>(R.id.recycle_movies)
        recycle_movies.setHasFixedSize(true)
        recycle_movies.layoutManager = mLayoutManagerM

        val mRecycleMovies = RecycleMovies(
            lista_movies,
            this
        )

        recycle_movies.adapter = mRecycleMovies

        recycle_movies?.addOnScrollListener(object : PaginationScrolled(mLayoutManagerM) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun carregarMaisItens() {
                isLoading = true

                onRetornoMovie(page!!.inc())
            }
        })


        viewModel.buscarGeneros(context!!, page!!)

        viewModel._isLoading.observe(this, Observer {
            if (it!!) progress.visibility = View.VISIBLE else progress.visibility = View.GONE
        })

        viewModel._listaGeneros.observe(this, Observer { it ->

            viewFrag.recycle_generos.also { res ->
                res.layoutManager = LinearLayoutManager(
                    context!!,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                res.setHasFixedSize(true)
                res.adapter = RecycleGeneros(
                    it,
                    this
                )
            }

        })

        viewModel._listaMovies.observe(this, Observer { it ->
            lista_movies.addAll(it)
            mRecycleMovies.notifyDataSetChanged()
            isLoading = false
            isLastPage = false

            activity!!.toolbar.subtitle = resources.getString(R.string.toolbar_sub_title)
                .replace("_TOTAL", String.format("%s", lista_movies.size))
        })

        /** DISPARA QUANDO OCORRE ERRO **/
        viewModel._isMessageError.observe(this, Observer { messagemDialog ->
            if (messagemDialog.isNotEmpty()) {
                Utils.mensagemSnack(
                    principal,
                    messagemDialog,
                    R.color.vermelho,
                    R.drawable.ic_fechar
                )
                viewFrag.fab_atualizar.visibility = View.VISIBLE
            } else
                viewFrag.fab_atualizar.visibility = View.GONE

        })

        viewModel._page.observe(this, Observer { p -> this.page = p })
    }



    override fun onRetornoMovie(page: Int?) {

        val lista_generos = (recycle_generos.adapter as RecycleGeneros).getList()
        val gen_filter = lista_generos.filter { it.checked!! }.map { it.id!! }.toCollection(arrayListOf())



        isLastPage = true
        viewModel.buscarFilmes(gen_filter.toIntArray(), context!!, page!!)
    }


    override fun onClear() {
        lista_movies.clear()
        val adapter = viewFrag.recycle_movies.adapter
        adapter!!.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        v?.visibility = View.GONE

        viewModel.buscarGeneros(context!!, page!!)
    }

    override fun onClickGeneros(view: View, generos: Generos, pos: Int) {
        val lista_generos = (recycle_generos.adapter as RecycleGeneros).getList()
        lista_generos[pos].checked = !generos.checked!!
        recycle_generos.adapter?.also { it.notifyItemChanged(pos) }

        onRetornoMovie(1)
        onClear()
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
        viewFrag.recycle_movies.adapter?.notifyItemChanged(viewModel.likeDeslike(movies, pos, like))
        Toast.makeText(
            context!!,
            (if (like) "Adicionado aos favoritos" else "Removido dos favoritos"),
            Toast.LENGTH_SHORT
        ).show()
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
            isLastPage = false
            if (p0!!.isNotEmpty())
                isLastPage = true

            val adapter = viewFrag.recycle_movies.adapter as RecycleMovies
            adapter.filter.filter(p0)
            return true
        } catch (err: Exception) {
            err.printStackTrace()
        }

        return false
    }

}