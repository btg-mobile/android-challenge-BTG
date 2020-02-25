package br.eloibrito.com.movie_db.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.databinding.RecycleGenerosBinding
import br.eloibrito.com.movie_db.models.Generos
import br.eloibrito.com.movie_db.ui.RecycleViewItemClick


class RecycleGeneros(
    private val generos: List<Generos>,
    private val listner: RecycleViewItemClick
) :
    RecyclerView.Adapter<RecycleGeneros.ViewHolder>() {

    inner class ViewHolder(val recycleBiding: RecycleGenerosBinding) :
        RecyclerView.ViewHolder(recycleBiding.root)

    override fun onCreateViewHolder(parent: ViewGroup, i: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycle_generos,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recycleBiding.generos = generos[position]

        holder.recycleBiding.txtGeneros.setBackgroundResource(if (generos[position].checked!!) R.drawable.border_color_text else R.drawable.border_color_text_normal)
        holder.recycleBiding.txtGeneros.setOnClickListener {
            listner.onClickGeneros(holder.recycleBiding.txtGeneros, generos[position], position)
        }
    }


    override fun getItemCount() = generos.size

    fun getList() = generos

}