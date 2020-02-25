package br.eloibrito.com.movie_db.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.data.dao.AppDataBase
import br.eloibrito.com.movie_db.models.Generos
import br.eloibrito.com.movie_db.models.Movies
import br.eloibrito.com.movie_db.data.network.ApiRetrofit
import br.eloibrito.com.movie_db.data.network.Callback
import br.eloibrito.com.movie_db.ui.adapter.PaginationScrolled
import br.eloibrito.com.movie_db.ui.adapter.RecycleGeneros
import br.eloibrito.com.movie_db.ui.adapter.RecycleMovies
import br.eloibrito.com.movie_db.data.repositorios.RepositoriosMovies
import br.eloibrito.com.movie_db.ui.filmes.ListMovieModel
import br.eloibrito.com.movie_db.ui.filmes.ListMovieModelFactory
import br.eloibrito.com.movie_db.utils.App
import br.eloibrito.com.movie_db.utils.CheckReadPermission
import br.eloibrito.com.movie_db.utils.Utils
import kotlinx.android.synthetic.main.layout_lista_movies.*
import kotlinx.android.synthetic.main.layout_movie.*
import kotlinx.android.synthetic.main.layout_movie.principal
import java.lang.Exception
import javax.inject.Inject

class ListMoviesActivityTeste : AppCompatActivity(),
    Callback, View.OnClickListener, SearchView.OnQueryTextListener, RecycleViewItemClick {

    @Inject
    lateinit var api: ApiRetrofit
    @Inject
    lateinit var db: AppDataBase

    var REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124

    init {
        (App).getComponent().inject(this)
    }

    lateinit var viewModel: ListMovieModel
    private var lista_movies = ArrayList<Movies>()
    private var page: Int? = 1
    var isLastPage: Boolean = false
    var isLoading: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_movie)

        if (!CheckReadPermission.show(this))
            return

        controles()

    }

    fun controles() {

        toolbar.title = resources.getString(R.string.app_name)
        setSupportActionBar(toolbar)
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

        val mLayoutManagerM = GridLayoutManager(this, 2)

        val recycle_movies = findViewById<RecyclerView>(R.id.recycle_movies)
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


        viewModel.buscarGeneros(applicationContext, page!!)

        viewModel._isLoading.observe(this, Observer {
            if (it!!) progress.visibility = View.VISIBLE else progress.visibility = View.GONE
        })

        viewModel._listaGeneros.observe(this, Observer { it ->

            recycle_generos.also { res ->
                res.layoutManager = LinearLayoutManager(
                    this@ListMoviesActivityTeste,
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

            toolbar.subtitle = resources.getString(R.string.toolbar_sub_title)
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
                fab_atualizar.visibility = View.VISIBLE
            } else
                fab_atualizar.visibility = View.GONE

        })

        viewModel._page.observe(this, Observer { p -> this.page = p })
    }

    override fun onRetornoMovie(page: Int?) {
        val generos_selecionados = ArrayList<Int>()
        val lista_generos = (recycle_generos.adapter as RecycleGeneros).getList()
        for (generos in lista_generos)
            if (generos.checked!!) {
                Log.e("GENEROS_SELECIONADOS", generos.id!!.toString())
                generos_selecionados.add(generos.id!!)
            }


        isLastPage = true
        viewModel.buscarFilmes(generos_selecionados.toIntArray(), applicationContext, page!!)
    }


    override fun onClear() {
        lista_movies.clear()
        val adapter = recycle_movies.adapter
        adapter!!.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        v?.visibility = View.GONE

        viewModel.buscarGeneros(applicationContext, page!!)
    }

    override fun onClickGeneros(view: View, generos: Generos, pos: Int) {
        val lista_generos = (recycle_generos.adapter as RecycleGeneros).getList()
        lista_generos[pos].checked = !generos.checked!!
        recycle_generos.adapter?.also { it.notifyItemChanged(pos) }

        onRetornoMovie(1)
        onClear()
    }

    override fun onClickMovies(view: View, movies: Movies, pos: Int) {
//        val intent = Intent(this@ListMoviesActivityTeste, ListMoviesDetailsActivity::class.java)
//        intent.putExtra(ListMoviesDetailsActivity.ID_DETAIL, movies)
//
//        val activityOptions: ActivityOptionsCompat =
//            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                this@ListMoviesActivityTeste,
//
//
//                Pair<View, String>(
//                    view,
//                    ListMoviesDetailsActivity.VIEW_NAME_HEADER_IMAGE
//                )
//            )
//
//        ActivityCompat.startActivity(this@ListMoviesActivityTeste, intent, activityOptions.toBundle())
    }

    override fun onClickMoviesLike(movies: Movies, pos: Int, like: Boolean) {
        recycle_movies.adapter?.notifyItemChanged(viewModel.likeDeslike(movies, pos, like))
        Toast.makeText(
            applicationContext,
            (if (like) "Adicionado aos favoritos" else "Removido dos favoritos"),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_buscar, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.buscar).actionView as SearchView


        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )

        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        try {
            isLastPage = false
            if (p0!!.isNotEmpty())
                isLastPage = true

            val adapter = recycle_movies.adapter as RecycleMovies
            adapter.filter.filter(p0)
            return true
        } catch (err: Exception) {
            err.printStackTrace()
        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    controles()

        }

    }


}