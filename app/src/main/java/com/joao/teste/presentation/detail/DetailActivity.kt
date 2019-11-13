package com.joao.teste.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.joao.data.util.Constants
import com.joao.domain.entity.MoviesItem
import com.joao.teste.R
import com.joao.teste.databinding.ActivityDetailBinding
import com.joao.teste.util.DialogFactory
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()

    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(detailViewModel)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = detailViewModel

        detailViewModel.model = intent.getSerializableExtra(MOVIE) as MoviesItem

        configActionBar()

        loadEvents()
    }

    private fun loadEvents() {
        val loading = DialogFactory.getLoading(this)
        detailViewModel.fetchGenres()
        detailViewModel.loading.observe(this, Observer {
            if (it)
                loading.show()
            else
                loading.dismiss()
        })
        detailViewModel.error.observe(this, Observer {
            DialogFactory.getDialogError(this, it).show()
        })
        detailViewModel.success.observe(this, Observer {
            Glide.with(this)
                .load(Constants.IMAGE_URL_ORIGINAL + detailViewModel.model?.backdropPath)
                .into(binding.ivDetail)
            binding.genre = it
            binding.model = detailViewModel.model

        })
        detailViewModel.update.observe(this, Observer {
            binding.ibStarDetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (it) R.drawable.ic_star else R.drawable.ic_star_border
                )
            )
        })
    }

    private fun configActionBar() {
        supportActionBar?.title = "Detalhes"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val MOVIE = "movie"
        fun startIntent(context: Context, movie: MoviesItem?) {
            context.startActivity(
                Intent(context, DetailActivity::class.java).putExtra(
                    MOVIE,
                    movie
                )
            )
        }
    }
}
