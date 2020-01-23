package com.rafaelpereiraramos.challengebtg.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.rafaelpereiraramos.challengebtg.view.R
import com.rafaelpereiraramos.challengebtg.view.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindLiveData()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getMovieDetails(args.id)
    }

    private fun bindLiveData() {
        viewModel.movie.observe(this, Observer {
            if (it != null) {
                prompt_title.text = it.title
                prompt_overview.text = it.overview
                prompt_vote_average.text = getString(R.string.detail_fragment_vote_average, it.voteAverage.toString())

                GlideApp.with(this)
                    .load("https://image.tmdb.org/t/p/w154/${it.coverUrl}")
                    .error(android.R.drawable.stat_notify_error)
                    .into(prompt_img_cover)
            }
        })

        viewModel.genres.observe(this, Observer {
            val builder = StringBuilder()
            it.forEach {genre -> builder.append(genre.name).append(" ") }
            prompt_genre.text = builder.toString()
        })
    }
}