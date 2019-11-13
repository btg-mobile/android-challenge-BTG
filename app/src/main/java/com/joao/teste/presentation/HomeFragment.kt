package com.joao.teste.presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.joao.data.util.Constants
import com.joao.domain.entity.MoviesItem
import com.joao.teste.R
import com.joao.teste.presentation.base.BaseAdapter
import com.joao.teste.presentation.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_movie.view.*

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private var listMovies = ArrayList<MoviesItem?>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_movies.layoutManager = LinearLayoutManager(context)
        with(activity as MainActivity) {
            adapterHome = BaseAdapter(R.layout.item_movie, listMovies) { viewAdapter, item ->
                viewAdapter.tv_name_movie.text = item?.title
                viewAdapter.tv_date.text = item?.releaseDate
                viewAdapter.ib_star.setImageResource(if (item?.isFavorite == true) R.drawable.ic_star else R.drawable.ic_star_border)
                Glide.with(this).load(Constants.IMAGE_URL + item?.posterPath)
                    .into(viewAdapter.iv_movie)

                viewAdapter.setOnClickListener {
                    DetailActivity.startIntent(
                        requireContext(),
                        item
                    )
                }
            }
            rv_movies.adapter = adapterHome

            moviesViewModel.updateList.observe(this, Observer {
                sw_layout.isRefreshing = false
                listMovies.clear()
                listMovies.addAll(it)
                this@HomeFragment.tv_empty_list.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                rv_movies?.adapter?.notifyDataSetChanged()
            })

            moviesViewModel.error.observe(this, Observer {
                tv_empty_list.visibility = View.VISIBLE
            })
        }

        sw_layout.setOnRefreshListener {
            (activity as MainActivity).moviesViewModel.fetchMovies()
        }
    }


    companion object {
        fun newInstance() = HomeFragment()
    }

}
