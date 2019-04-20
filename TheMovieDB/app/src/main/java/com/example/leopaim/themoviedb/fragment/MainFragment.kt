package com.example.leopaim.themoviedb.fragment

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.leopaim.themoviedb.R
import com.example.leopaim.themoviedb.database.AppDatabase
import com.example.leopaim.themoviedb.database.GenreDao
import com.example.leopaim.themoviedb.database.MovieDao
import com.example.leopaim.themoviedb.activity.DetailActivity
import com.example.leopaim.themoviedb.model.Genre
import com.example.leopaim.themoviedb.model.Movie
import com.example.leopaim.themoviedb.network.ApiRepository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_main.*
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import android.view.inputmethod.InputMethodManager
import android.support.v7.widget.RecyclerView
import com.example.leopaim.themoviedb.activity.MainActivity
import com.example.leopaim.themoviedb.main.MainAdapter
import com.example.leopaim.themoviedb.main.MainPresenter


class MainFragment : Fragment(){

    private lateinit var presenter : MainPresenter
    private lateinit var adapter: MainAdapter
    private var movies : MutableList<Movie> = mutableListOf()
    private lateinit var movieDao: MovieDao
    private lateinit var genreDao: GenreDao

    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable
    private var page: Int = 1

    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val database = Room.databaseBuilder(context!!, AppDatabase::class.java, resources.getString(R.string.db_name))
            .allowMainThreadQueries()
            .build()

        movieDao = database.movieDao()
        genreDao = database.genreDao()

        return inflater.inflate(R.layout.fragment_main,container,false)
    }

    override fun onStart() {
        super.onStart()

        //Load the view
        mHandler = Handler()
        swipeContainer.isRefreshing = true
        mRunnable = Runnable {
            swipeContainer.isRefreshing = false
        }

        updateView()
        search()

        swipeContainer.setOnRefreshListener {
            page = 1
            updateView()
            mRunnable = Runnable {
                swipeContainer.isRefreshing = false
            }
        }

        //Get event of onPageSelected
        val container = (this.activity as MainActivity).findViewById(R.id.container) as ViewPager

        container?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    updateView()
                    search()
                }
            }

        })


    }

    fun showMovieList(data: List<Movie>) {
        mHandler.post(mRunnable);
        movies.clear()
        movies.addAll(data)
        adapter.notifyDataSetChanged()
    }

    fun showMoreMovieList(data: List<Movie>) {
        movies.addAll(data)
        adapter.notifyDataSetChanged()
        progress.visibility = ProgressBar.GONE
        loading = true
    }

    fun showGenreList(data: List<Genre>) {
        genreDao.addAll(data)
    }

    fun updateView(){
        val dbMovies = movieDao.all()
        adapter = MainAdapter(movies, dbMovies, {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("MOVIE", it)
            startActivity(intent)
        }, { movie: Movie, favorite: ImageView ->
            if (movieDao.hasFavorite(movie.id)) {
                movieDao.delete(movie)
                favorite.setImageResource(R.drawable.unfavorite);
            } else {
                movieDao.add(movie)
                favorite.setImageResource(R.drawable.favorite);
            }
        })

        movieList.adapter = adapter

        presenter = MainPresenter(this, ApiRepository(), Gson())
        presenter.getMovieList(page.toString())
        presenter.getGenreList()

        val mLayoutManager: LinearLayoutManager
        mLayoutManager = GridLayoutManager(context, 2)
        movieList.setLayoutManager(mLayoutManager)

        //Update RecyclerView when user get to the end of the list
        movieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                {
                    visibleItemCount = mLayoutManager.getChildCount()
                    totalItemCount = mLayoutManager.getItemCount()
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            page += 1
                            presenter.getMovieList(page.toString())
                            progress.visibility = ProgressBar.VISIBLE
                        }
                    }
                }
            }
        })
    }

    fun search(){
        val searchView = (this.activity as MainActivity).findViewById(R.id.searchView) as EditText
        val searchButton = (this.activity as MainActivity).findViewById(R.id.searchIcon) as ImageView
        val filterButton = (this.activity as MainActivity).findViewById(R.id.filter) as ImageView

        searchButton.setOnClickListener {
            if (searchView.isFocused) {
                searchButton.setImageResource(R.drawable.search)
                searchView.setText(context!!.resources.getString(R.string.app_name))
                val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(searchView.windowToken, InputMethodManager.SHOW_IMPLICIT);
                searchView.clearFocus()
            } else {
                searchView.requestFocus()
                searchView.setText(" ")
                val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
                searchButton.setImageResource(R.drawable.close)

            }

        }

        filterButton.setOnClickListener {
            //TODO Implementar filtros
        }


        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //
                if (count > 2 && !s.toString().equals(context!!.resources.getString(R.string.app_name))) {
                    presenter.searchMovie(s.toString(), page.toString())
                    swipeContainer.isRefreshing = true
                    page = 1
                    mRunnable = Runnable {
                        swipeContainer.isRefreshing = false
                    }
                }

            }

        })
    }










}