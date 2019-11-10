package com.desafio.marvelapp.comics.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.desafio.marvelapp.R
import com.desafio.marvelapp.base.BaseRecyclerView
import com.desafio.marvelapp.comics.ui.ComicsPresenter
import com.desafio.marvelapp.data.repository.KeyDatabase
import com.desafio.marvelapp.detailComic.DetailComicActivity
import com.desafio.marvelapp.model.Favorite
import com.desafio.marvelapp.model.MarvelComic
import com.desafio.marvelapp.util.MarvelUtil

class ComicsAdapter(private val presenter: ComicsPresenter, private val appCompatActivity: AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BaseRecyclerView {
    private lateinit var context: Context
    private var comics = ArrayList<MarvelComic>()
    private var comicsOriginal = ArrayList<MarvelComic>()
    private var showLoader: Boolean = false
    private var listForLetter: Boolean = false

    override fun isListForLetter() = listForLetter

    fun setComics(comics: List<MarvelComic>, listForLetter: Boolean) {
        this.comics.addAll(comics)
        this.comicsOriginal.addAll(comics)
        this.listForLetter = listForLetter
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (!this.listForLetter &&
                comics.size == comicsOriginal.size &&
                position != 0 &&
                (MarvelUtil.isPortrait(appCompatActivity) && (position == itemCount - 1 || position == itemCount - 2)) ||
                (MarvelUtil.isLand(appCompatActivity) && (position == itemCount - 1 || position == itemCount - 2 || position == itemCount - 3))) {
            return VIEW_LOADER
        }

        return VIEW_ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        return if (viewType == VIEW_ITEM) {
            ComicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))
        } else {
            LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loader_item_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (comics.isNotEmpty()) {
            if (holder is ComicViewHolder) {
                val marvelCharacter = comics[position]
                holder.name.text = marvelCharacter.title
                MarvelUtil.setImageUrl(context, marvelCharacter.thumbMail?.getPathExtension(), holder.image)
                holder.favorite.setButtonDrawable(toggleImage(marvelCharacter.favorite != null))
                holder.favorite.isChecked = marvelCharacter.favorite != null
                holder.favorite.setOnClickListener { view -> toggleFavorite(position, view) }
                holder.image.setOnClickListener { showDetail(holder, marvelCharacter) }
            } else {
                if (showLoader) {
                    (holder as LoaderViewHolder).progressBar.visibility = View.VISIBLE
                } else {
                    (holder as LoaderViewHolder).progressBar.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun toggleFavorite(position: Int, view: View) {
        val checkView = view as CheckBox
        val comics = comics[position]
        checkView.setButtonDrawable(toggleImage(checkView.isChecked))

        if (comics.favorite == null) {
            comics.favorite = Favorite(comics.id)
            comics.favorite?.groupType = KeyDatabase.FavoriteGroup.COMICS
            comics.favorite?.extension = comics.thumbMail?.extension
            comics.favorite?.path = comics.thumbMail?.path
            comics.favorite?.name = comics.title
        }
        comics.favorite?.let { presenter.toggleFavorite(it, checkView.isChecked) }
    }

    override fun toggleImage(checked: Boolean): Int {
        return if (checked) R.drawable.ic_star_white_24px else R.drawable.ic_star_border_white_24px
    }

    @SuppressLint("NewApi")
    private fun showDetail(holder: ComicViewHolder, marvelComic: MarvelComic) {
        val detailIntent = Intent(context, DetailComicActivity::class.java)
        detailIntent.putExtra(KeyDatabase.ID, marvelComic.id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imagePair = Pair.create(holder.image as View, holder.image.transitionName)
            val holderPair = Pair.create(holder.name as View, holder.name.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(appCompatActivity, imagePair, holderPair)
            ActivityCompat.startActivity(context, detailIntent, options.toBundle())
        } else {
            context.startActivity(detailIntent)
        }
    }

    fun showLoading(status: Boolean) {
        showLoader = status
    }

    override fun clear() {
        comics.clear()
        comicsOriginal.clear()
        notifyDataSetChanged()
    }

    override fun filter(text: String?) {
        comics.clear()
        text?.let {
            if (it.isEmpty()) {
                comics.addAll(comicsOriginal)
            } else {
                comicsOriginal.filter { it.title != null && it.title.toString().contains(text, true) }
                        .map { comics.add(it) }
            }
            notifyDataSetChanged()
        }
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var name = itemView.findViewById(R.id.name_item) as TextView
        var favorite = itemView.findViewById(R.id.check_favorite) as CheckBox
    }

    inner class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar = itemView.findViewById(R.id.progressbar) as ProgressBar
    }

    companion object {
        private const val VIEW_ITEM = 1
        private const val VIEW_LOADER = 2
    }
}