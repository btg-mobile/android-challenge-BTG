package org.challenge.btg.ui.presentation


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies.view.*
import org.challenge.btg.R
import org.challenge.btg.ui.presentation.adapter.MovieAdapter

class MoviesFragment : Fragment() {

    lateinit var adapterRecycleViewMovies: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_movies, container, false)

        val recyclerView = rootView.recyclerView as RecyclerView
        recyclerView.adapter = MovieAdapter(emptyList())
        adapterRecycleViewMovies = recyclerView.adapter as MovieAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(rootView.context, "Carregando página ${adapterRecycleViewMovies.getPage().toString()}, aguarde...", Toast.LENGTH_SHORT).show()
                    adapterRecycleViewMovies.moreListMovies()
                }
            }
        })

        val editSearch = rootView.edit_search as EditText
        editSearch.hint = R.string.find_name.toString()
        editSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapterRecycleViewMovies.setFilter(charSequence.toString())
            }
        })

        return rootView
    }

    override fun onResume() {
        adapterRecycleViewMovies.updateListMovies()
        super.onResume()
    }

}
